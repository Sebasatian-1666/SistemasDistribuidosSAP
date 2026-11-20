import java.io.*;
import java.net.*;
import java.sql.*;

public class ServidorPagos {
    public static void main(String[] args) throws Exception {
        String url = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/db_multas");
        String user = System.getenv().getOrDefault("DB_USER", "root");
        String pass = System.getenv().getOrDefault("DB_PASSWORD", "");

        try (ServerSocket server = new ServerSocket(5000)) {
            System.out.println("Socket de pagos en puerto 5000");
            while (true) {
                try (Socket client = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                    String[] data = in.readLine().split("\\|", 2);
                    if (data.length != 2) {
                        out.println("ERROR|Formato esperado: codigo|ci");
                        continue;
                    }

                    try (Connection db = DriverManager.getConnection(url, user, pass);
                         PreparedStatement ps = db.prepareStatement(
                             "UPDATE multas SET estado='pagada', updated_at=NOW() " +
                             "WHERE codigo=? AND ci_conductor=? AND estado='pendiente'")) {
                        ps.setString(1, data[0].trim());
                        ps.setString(2, data[1].trim());
                        out.println(ps.executeUpdate() == 1 ? "OK|pagada" : "ERROR|No existe, CI incorrecta o ya fue pagada/anulada");
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
