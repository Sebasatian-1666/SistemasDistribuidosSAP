using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web.Services;
using Newtonsoft.Json;

namespace ServidorPagos
{
    public class Factura
    {
        public int id { get; set; }
        public string ci { get; set; }
        public string nombre { get; set; }
        public string servicio { get; set; }
        public double monto { get; set; }
        public string estado { get; set; }
        public string mes { get; set; }
    }

    public class Deuda : Factura
    {
        public string origen { get; set; }
    }

    public class RespuestaFacturas
    {
        public bool exito { get; set; }
        public string mensaje { get; set; }
        public List<Factura> facturas { get; set; }
    }

    public class RespuestaPago
    {
        public bool exito { get; set; }
        public string mensaje { get; set; }
        public string ci { get; set; }
        public int factura_id { get; set; }
        public string servicio { get; set; }
        public string fecha_pago { get; set; }
    }

    public class Persona
    {
        public string CI { get; set; }
        public string Nombres { get; set; }
        public string PrimerApellido { get; set; }
        public string SegundoApellido { get; set; }
    }

    public class GraphQLResponse
    {
        public GraphQLData data { get; set; }
    }

    public class GraphQLData
    {
        public Persona buscarPorCI { get; set; }
    }

    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class Pagos : WebService
    {
        private static readonly HttpClient client = new HttpClient();
        private const string URL_CESSA = "http://localhost:8000/api";
        private const string URL_ELAPAS = "http://localhost:8001/api";
        private const string URL_SEGIP = "http://localhost:4000/graphql";

        private async Task<Persona> ConsultarSegip(string ci)
        {
            var contenido = new StringContent(JsonConvert.SerializeObject(new { query = "{ buscarPorCI(CI: \"" + ci + "\") { CI Nombres PrimerApellido SegundoApellido } }" }), Encoding.UTF8, "application/json");
            var response = await client.PostAsync(URL_SEGIP, contenido);
            var resultado = JsonConvert.DeserializeObject<GraphQLResponse>(await response.Content.ReadAsStringAsync());
            return resultado?.data?.buscarPorCI;
        }

        private async Task<RespuestaFacturas> ConsultarFacturas(string url, string ci)
        {
            var contenido = new StringContent(JsonConvert.SerializeObject(new { ci = ci }), Encoding.UTF8, "application/json");
            var response = await client.GetAsync(url + "/facturas/" + Uri.EscapeDataString(ci));
            var resultado = JsonConvert.DeserializeObject<RespuestaFacturas>(await response.Content.ReadAsStringAsync());
            return resultado;
        }

        [WebMethod]
        public List<Deuda> verDeuda(string ci, string nombres, string primerApellido, string segundoApellido)
        {
            return VerDeudaAsync(ci, nombres, primerApellido, segundoApellido).GetAwaiter().GetResult();
        }

        [WebMethod]
        public List<Deuda> VerDeuda(string ci, string nombres, string primerApellido, string segundoApellido)
        {
            return verDeuda(ci, nombres, primerApellido, segundoApellido);
        }

        private async Task<List<Deuda>> VerDeudaAsync(string ci, string nombres, string primerApellido, string segundoApellido)
        {
            Persona persona = await ConsultarSegip(ci);

            if (persona == null)
                throw new Exception("CI no encontrado en SEGIP");

            if (!string.Equals(persona.Nombres, nombres, StringComparison.OrdinalIgnoreCase) ||
                !string.Equals(persona.PrimerApellido, primerApellido, StringComparison.OrdinalIgnoreCase) ||
                !string.Equals(persona.SegundoApellido, segundoApellido, StringComparison.OrdinalIgnoreCase))
                throw new Exception("Los datos no coinciden con SEGIP");

            List<Deuda> deudas = new List<Deuda>();
            RespuestaFacturas facturasCessa = await ConsultarFacturas(URL_CESSA, ci);
            RespuestaFacturas facturasElapas = await ConsultarFacturas(URL_ELAPAS, ci);

            if (facturasCessa?.exito == true && facturasCessa.facturas != null)
                foreach (Factura factura in facturasCessa.facturas)
                    deudas.Add(CrearDeuda(factura, "CESSA"));

            if (facturasElapas?.exito == true && facturasElapas.facturas != null)
                foreach (Factura factura in facturasElapas.facturas)
                    deudas.Add(CrearDeuda(factura, "ELAPAS"));

            return deudas;
        }

        private Deuda CrearDeuda(Factura factura, string origen)
        {
            return new Deuda
            {
                id = factura.id,
                ci = factura.ci,
                nombre = factura.nombre,
                servicio = factura.servicio,
                monto = factura.monto,
                estado = factura.estado,
                mes = factura.mes,
                origen = origen
            };
        }

        [WebMethod]
        public RespuestaPago PagarDeuda(Deuda c)
        {
            return PagarDeudaAsync(c).GetAwaiter().GetResult();
        }

        private async Task<RespuestaPago> PagarDeudaAsync(Deuda deuda)
        {
            string url = deuda.origen == "CESSA" ? URL_CESSA : URL_ELAPAS;
            var contenido = new StringContent(JsonConvert.SerializeObject(new { ci = deuda.ci, factura_id = deuda.id }), Encoding.UTF8, "application/json");
            var response = await client.PostAsync(url + "/facturas/pagar", contenido);
            var resultado = JsonConvert.DeserializeObject<RespuestaPago>(await response.Content.ReadAsStringAsync());
            return resultado;
        }
    }
}
