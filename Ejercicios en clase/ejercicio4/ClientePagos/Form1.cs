using System;
using System.Data;
using System.Windows.Forms;
using ClientePagos.ServicioPagosWS;

namespace ClientePagos
{
    public partial class Form1 : Form
    {
        private Deuda[] deudasActuales;

        public Form1()
        {
            InitializeComponent();
        }

        private void btnVerDeudas_Click(object sender, EventArgs e)
        {
            try
            {
                ServicioPagos servicio = new ServicioPagos();
                deudasActuales = servicio.VerDeuda(txtCI.Text, txtNombres.Text, txtPrimerApellido.Text, txtSegundoApellido.Text);

                DataTable tabla = new DataTable();
                tabla.Columns.Add("ID");
                tabla.Columns.Add("CI");
                tabla.Columns.Add("Nombre");
                tabla.Columns.Add("Servicio");
                tabla.Columns.Add("Monto");
                tabla.Columns.Add("Estado");
                tabla.Columns.Add("Mes");
                tabla.Columns.Add("Origen");

                foreach (var d in deudasActuales)
                {
                    tabla.Rows.Add(d.id, d.ci, d.nombre, d.servicio, d.monto, d.estado, d.mes, d.origen);
                }

                dgvDeudas.DataSource = tabla;
                lblResultado.Text = "Se encontraron " + deudasActuales.Length + " deudas";
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error");
                lblResultado.Text = "Error: " + ex.Message;
            }
        }

        private void btnPagar_Click(object sender, EventArgs e)
        {
            try
            {
                if (dgvDeudas.SelectedRows.Count == 0)
                {
                    MessageBox.Show("Seleccione una deuda de la lista", "Aviso");
                    return;
                }

                int indice = dgvDeudas.SelectedRows[0].Index;
                Deuda deudaSeleccionada = deudasActuales[indice];

                ServicioPagos servicio = new ServicioPagos();
                RespuestaPago resultado = servicio.PagarDeuda(deudaSeleccionada);

                MessageBox.Show(resultado.mensaje + "\nFecha: " + resultado.fecha_pago, "Pago Realizado");
                lblResultado.Text = resultado.mensaje;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error");
                lblResultado.Text = "Error: " + ex.Message;
            }
        }
    }
}
