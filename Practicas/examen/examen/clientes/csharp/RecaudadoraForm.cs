using System;
using System.IO;
using System.Net.Sockets;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows.Forms;

// Copia el handler a un Form Windows Forms con txtCodigo, txtCI y btnPagar.
public partial class RecaudadoraForm : Form
{
    private static readonly HttpClient http = new HttpClient();

    public RecaudadoraForm()
    {
        InitializeComponent();
        btnPagar.Click += btnPagar_Click;
    }

    private async void btnPagar_Click(object sender, EventArgs e)
    {
        try
        {
            string codigo = txtCodigo.Text.Trim();
            string ci = txtCI.Text.Trim();
            const string query = "query($codigo:String!, $ci:String!){ pagable(codigo:$codigo, ci:$ci) }";
            var body = JsonSerializer.Serialize(new { query, variables = new { codigo, ci } });
            using var response = await http.PostAsync("http://localhost:4000/graphql",
                new StringContent(body, Encoding.UTF8, "application/json"));
            response.EnsureSuccessStatusCode();
            using var doc = JsonDocument.Parse(await response.Content.ReadAsStringAsync());

            if (doc.RootElement.TryGetProperty("errors", out _) ||
                !doc.RootElement.GetProperty("data").GetProperty("pagable").GetBoolean())
            {
                MessageBox.Show("Multa inexistente, CI incorrecta o multa ya pagada/anulada.");
                return;
            }

            using var socket = new TcpClient();
            await socket.ConnectAsync("localhost", 5000);
            var utf8 = new UTF8Encoding(false);
            using var writer = new StreamWriter(socket.GetStream(), utf8) { AutoFlush = true };
            using var reader = new StreamReader(socket.GetStream(), utf8);
            await writer.WriteLineAsync(codigo + "|" + ci);
            string result = await reader.ReadLineAsync();
            MessageBox.Show(result == "OK|pagada" ? "Pago registrado" : result);
        }
        catch (Exception ex)
        {
            MessageBox.Show("No se pudo pagar: " + ex.Message);
        }
    }
}
