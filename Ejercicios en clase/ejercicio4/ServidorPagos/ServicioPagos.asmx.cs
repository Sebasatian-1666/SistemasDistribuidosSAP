using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
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

    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class ServicioPagos : WebService
    {
        private static readonly HttpClient client = new HttpClient();
        private const string BASE_URL = "http://localhost:8000/api";

        [WebMethod]
        public List<Factura> ObtenerFacturasCessa(string ci)
        {
            var response = client.GetAsync(BASE_URL + "/cessa/facturas/" + ci).Result;
            var json = response.Content.ReadAsStringAsync().Result;
            var resultado = JsonConvert.DeserializeObject<RespuestaFacturas>(json);
            return resultado.facturas;
        }

        [WebMethod]
        public List<Factura> ObtenerFacturasElapas(string ci)
        {
            var response = client.GetAsync(BASE_URL + "/elapas/facturas/" + ci).Result;
            var json = response.Content.ReadAsStringAsync().Result;
            var resultado = JsonConvert.DeserializeObject<RespuestaFacturas>(json);
            return resultado.facturas;
        }

        [WebMethod]
        public RespuestaPago PagarFacturaCessa(string ci, int facturaId)
        {
            var datos = new StringContent(JsonConvert.SerializeObject(new { ci = ci, factura_id = facturaId }), Encoding.UTF8, "application/json");
            var response = client.PostAsync(BASE_URL + "/cessa/facturas/pagar", datos).Result;
            var resultado = JsonConvert.DeserializeObject<RespuestaPago>(response.Content.ReadAsStringAsync().Result);
            return resultado;
        }

        [WebMethod]
        public RespuestaPago PagarFacturaElapas(string ci, int facturaId)
        {
            var datos = new StringContent(JsonConvert.SerializeObject(new { ci = ci, factura_id = facturaId }), Encoding.UTF8, "application/json");
            var response = client.PostAsync(BASE_URL + "/elapas/facturas/pagar", datos).Result;
            var resultado = JsonConvert.DeserializeObject<RespuestaPago>(response.Content.ReadAsStringAsync().Result);
            return resultado;
        }
    }
}
