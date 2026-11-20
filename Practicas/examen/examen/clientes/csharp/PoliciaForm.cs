using System;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml.Linq;
using System.Linq;

// Pega este archivo como Form1.cs dentro del proyecto Windows Forms.
// En el diseñador crea txtCI, txtNombre, txtInfraccion, txtLugar, txtMonto y btnRegistrar.
public partial class Form1 : Form
{
    private static readonly HttpClient http = new HttpClient();

    public Form1()
    {
        InitializeComponent();
        btnRegistrar.Click += btnRegistrar_Click;
    }

    private async void btnRegistrar_Click(object sender, EventArgs e)
    {
        try
        {
            string ci = txtCI.Text.Trim();
            if (!await VerificarCiSoap(ci))
            {
                MessageBox.Show("CI no válido según SEGIP");
                return;
            }

            var multa = new
            {
                codigo = "M-" + DateTime.Now.ToString("yyyyMMddHHmmss"),
                ci_conductor = ci,
                nombre_conductor = txtNombre.Text.Trim(),
                fecha = DateTime.Today.ToString("yyyy-MM-dd"),
                tipo_infraccion = txtInfraccion.Text.Trim(),
                lugar = txtLugar.Text.Trim(),
                monto = decimal.Parse(txtMonto.Text)
            };

            using var body = new StringContent(JsonSerializer.Serialize(multa), Encoding.UTF8, "application/json");
            using var response = await http.PostAsync("http://localhost:8000/api/multas", body);
            string result = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode) throw new Exception(result);
            MessageBox.Show("Multa registrada: " + multa.codigo);
        }
        catch (Exception ex)
        {
            MessageBox.Show("No se pudo registrar: " + ex.Message);
        }
    }

    private static async Task<bool> VerificarCiSoap(string ci)
    {
        const string endpoint = "http://localhost/VerificarCI.asmx";
        string envelope = $@"<?xml version=""1.0"" encoding=""utf-8""?>
<soap:Envelope xmlns:soap=""http://schemas.xmlsoap.org/soap/envelope/"">
  <soap:Body><verificarCI xmlns=""http://segip.local/""><ci>{System.Security.SecurityElement.Escape(ci)}</ci></verificarCI></soap:Body>
</soap:Envelope>";
        using var request = new HttpRequestMessage(HttpMethod.Post, endpoint);
        request.Content = new StringContent(envelope, Encoding.UTF8, "text/xml");
        request.Headers.Add("SOAPAction", "\"http://segip.local/verificarCI\"");
        using var response = await http.SendAsync(request);
        response.EnsureSuccessStatusCode();
        var xml = XDocument.Parse(await response.Content.ReadAsStringAsync());
        var value = xml.Descendants().FirstOrDefault(x => x.Name.LocalName == "verificarCIResult")?.Value;
        return bool.TryParse(value, out bool valid) && valid;
    }
}
