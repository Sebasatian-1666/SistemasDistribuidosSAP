/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.practicas2.soquetsrmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
/**
 *
 * @author usuario
 */
public class ClienteJuez {

 public static void main(String[] args) {
        String ipJusticia = args.length > 0 ? args[0] : "10.170.34.168";
        Scanner sc = new Scanner(System.in);

        try {
            Registry registro = LocateRegistry.getRegistry(ipJusticia, 1099);
            IServidorJusticia justicia = (IServidorJusticia) registro.lookup("Justicia");

            System.out.println("=== Conectado al Servidor Justicia (" + ipJusticia + ") ===");

            boolean salir = false;
            while (!salir) {
                System.out.println("\n1. Consultar cuentas");
                System.out.println("2. Congelar cuenta");
                System.out.println("3. Salir");
                System.out.print("Opcion: ");
                String opcion = sc.nextLine().trim();

                switch (opcion) {
                    case "1":
                        consultar(justicia, sc);
                        break;
                    case "2":
                        congelar(justicia, sc);
                        break;
                    case "3":
                        salir = true;
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error de conexion con Justicia: " + e.getMessage());
        }
    }

    private static void consultar(IServidorJusticia justicia, Scanner sc) throws Exception {
        System.out.print("CI: ");
        String ci = sc.nextLine().trim();
        System.out.print("Nombres: ");
        String nombres = sc.nextLine().trim();
        System.out.print("Apellidos: ");
        String apellidos = sc.nextLine().trim();

        RespuestaCuenta resp = justicia.ConsultarCuentas(ci, nombres, apellidos);

        if (resp.isError()) {
            System.out.println("ERROR: " + resp.getMensaje());
        } else {
            System.out.println("--- Cuentas encontradas ---");
            for (Cuenta c : resp.getCuentas()) {
                System.out.println(c);
            }
        }
    }

    private static void congelar(IServidorJusticia justicia, Scanner sc) throws Exception {
        System.out.print("Numero de cuenta: ");
        String nrocuenta = sc.nextLine().trim();
        System.out.print("Banco (MERCANTIL / BCP): ");
        Banco banco = Banco.valueOf(sc.nextLine().trim().toUpperCase());
        System.out.print("Monto a congelar: ");
        Double monto = Double.parseDouble(sc.nextLine().trim());

        RespuestaCuenta resp = justicia.Congelar(nrocuenta, banco, monto);

        if (resp.isError()) {
            System.out.println("ERROR: " + resp.getMensaje());
        } else {
            System.out.println("OK: " + resp.getMensaje());
        }
    }
}
