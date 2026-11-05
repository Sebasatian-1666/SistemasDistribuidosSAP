using System.Data;
using System.Web.Services;
using System.Web.Services.Protocols;

namespace ClientePagos.ServicioPagosWS
{
    public class Deuda
    {
        public int id { get; set; }
        public string ci { get; set; }
        public string nombre { get; set; }
        public string servicio { get; set; }
        public double monto { get; set; }
        public string estado { get; set; }
        public string mes { get; set; }
        public string origen { get; set; }
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

    [System.Web.Services.WebServiceBindingAttribute(Name = "ServicioPagosSoap", Namespace = "http://tempuri.org/")]
    public class ServicioPagos : SoapHttpClientProtocol
    {
        public ServicioPagos()
        {
            this.Url = "http://localhost:5050/ServicioPagos.asmx";
        }

        [SoapDocumentMethodAttribute("http://tempuri.org/VerDeuda")]
        public Deuda[] VerDeuda(string ci, string nombres, string primerApellido, string segundoApellido)
        {
            object[] results = this.Invoke("VerDeuda", new object[] { ci, nombres, primerApellido, segundoApellido });
            return (Deuda[])results[0];
        }

        [SoapDocumentMethodAttribute("http://tempuri.org/PagarDeuda")]
        public RespuestaPago PagarDeuda(Deuda c)
        {
            object[] results = this.Invoke("PagarDeuda", new object[] { c });
            return (RespuestaPago)results[0];
        }
    }
}
