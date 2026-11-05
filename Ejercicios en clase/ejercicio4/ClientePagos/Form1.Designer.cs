namespace ClientePagos
{
    partial class Form1
    {
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.Label lblCI;
        private System.Windows.Forms.TextBox txtCI;
        private System.Windows.Forms.Label lblNombres;
        private System.Windows.Forms.TextBox txtNombres;
        private System.Windows.Forms.Label lblPrimerApellido;
        private System.Windows.Forms.TextBox txtPrimerApellido;
        private System.Windows.Forms.Label lblSegundoApellido;
        private System.Windows.Forms.TextBox txtSegundoApellido;
        private System.Windows.Forms.Button btnVerDeudas;
        private System.Windows.Forms.DataGridView dgvDeudas;
        private System.Windows.Forms.Button btnPagar;
        private System.Windows.Forms.Label lblResultado;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        private void InitializeComponent()
        {
            this.lblCI = new System.Windows.Forms.Label();
            this.txtCI = new System.Windows.Forms.TextBox();
            this.lblNombres = new System.Windows.Forms.Label();
            this.txtNombres = new System.Windows.Forms.TextBox();
            this.lblPrimerApellido = new System.Windows.Forms.Label();
            this.txtPrimerApellido = new System.Windows.Forms.TextBox();
            this.lblSegundoApellido = new System.Windows.Forms.Label();
            this.txtSegundoApellido = new System.Windows.Forms.TextBox();
            this.btnVerDeudas = new System.Windows.Forms.Button();
            this.dgvDeudas = new System.Windows.Forms.DataGridView();
            this.btnPagar = new System.Windows.Forms.Button();
            this.lblResultado = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dgvDeudas)).BeginInit();
            this.SuspendLayout();

            this.lblCI.AutoSize = true;
            this.lblCI.Location = new System.Drawing.Point(20, 20);
            this.lblCI.Text = "CI:";

            this.txtCI.Location = new System.Drawing.Point(160, 17);
            this.txtCI.Size = new System.Drawing.Size(200, 22);
            this.txtCI.Text = "1234567";

            this.lblNombres.AutoSize = true;
            this.lblNombres.Location = new System.Drawing.Point(20, 55);
            this.lblNombres.Text = "Nombres:";

            this.txtNombres.Location = new System.Drawing.Point(160, 52);
            this.txtNombres.Size = new System.Drawing.Size(200, 22);
            this.txtNombres.Text = "Juan Carlos";

            this.lblPrimerApellido.AutoSize = true;
            this.lblPrimerApellido.Location = new System.Drawing.Point(20, 90);
            this.lblPrimerApellido.Text = "Primer Apellido:";

            this.txtPrimerApellido.Location = new System.Drawing.Point(160, 87);
            this.txtPrimerApellido.Size = new System.Drawing.Size(200, 22);
            this.txtPrimerApellido.Text = "Perez";

            this.lblSegundoApellido.AutoSize = true;
            this.lblSegundoApellido.Location = new System.Drawing.Point(20, 125);
            this.lblSegundoApellido.Text = "Segundo Apellido:";

            this.txtSegundoApellido.Location = new System.Drawing.Point(160, 122);
            this.txtSegundoApellido.Size = new System.Drawing.Size(200, 22);
            this.txtSegundoApellido.Text = "Gomez";

            this.btnVerDeudas.Location = new System.Drawing.Point(400, 20);
            this.btnVerDeudas.Size = new System.Drawing.Size(180, 45);
            this.btnVerDeudas.Text = "Ver Deudas";
            this.btnVerDeudas.Click += new System.EventHandler(this.btnVerDeudas_Click);

            this.dgvDeudas.Location = new System.Drawing.Point(20, 165);
            this.dgvDeudas.Size = new System.Drawing.Size(750, 250);
            this.dgvDeudas.AllowUserToAddRows = false;
            this.dgvDeudas.ReadOnly = true;
            this.dgvDeudas.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvDeudas.MultiSelect = false;
            this.dgvDeudas.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;

            this.btnPagar.Location = new System.Drawing.Point(400, 80);
            this.btnPagar.Size = new System.Drawing.Size(180, 45);
            this.btnPagar.Text = "Pagar Seleccionada";
            this.btnPagar.Click += new System.EventHandler(this.btnPagar_Click);

            this.lblResultado.AutoSize = true;
            this.lblResultado.Location = new System.Drawing.Point(20, 430);
            this.lblResultado.MaximumSize = new System.Drawing.Size(750, 0);
            this.lblResultado.Text = "";

            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 470);
            this.Controls.Add(this.lblCI);
            this.Controls.Add(this.txtCI);
            this.Controls.Add(this.lblNombres);
            this.Controls.Add(this.txtNombres);
            this.Controls.Add(this.lblPrimerApellido);
            this.Controls.Add(this.txtPrimerApellido);
            this.Controls.Add(this.lblSegundoApellido);
            this.Controls.Add(this.txtSegundoApellido);
            this.Controls.Add(this.btnVerDeudas);
            this.Controls.Add(this.dgvDeudas);
            this.Controls.Add(this.btnPagar);
            this.Controls.Add(this.lblResultado);
            this.Text = "Cliente Pagos - Sistema Distribuido";
            ((System.ComponentModel.ISupportInitialize)(this.dgvDeudas)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();
        }
    }
}
