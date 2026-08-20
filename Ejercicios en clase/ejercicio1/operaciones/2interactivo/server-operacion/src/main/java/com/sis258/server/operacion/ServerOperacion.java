/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.sis258.server.operacion;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerOperacion {

    public static void main(String[] args) {
        int port = 5002;
        ServerSocket server;
        
        try {
            // 1. Iniciar el servidor UNA SOLA VEZ fuera del bucle
            server = new ServerSocket(port);
            System.out.println("Se inicio el servidor interactivo con éxito en el puerto " + port);
            
            while (true) {
                Socket client;
                PrintStream toClient;
                
                // El servidor espera la conexión
                client = server.accept(); 
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                toClient = new PrintStream(client.getOutputStream());
                
                System.out.println("\n--- Nuevo cliente conectado ---");
                
                try {
                    // PASO 1: Recibe el primer número
                    String recibido = fromClient.readLine();
                    System.out.println("El cliente envio el primer numero: " + recibido);
                    int numero1 = Integer.parseInt(recibido);

                    // PASO 2: Pide el segundo número y lo recibe
                    toClient.println("Introduzca el segundo numero:");
                    String recibido2 = fromClient.readLine();
                    System.out.println("El cliente envio el segundo numero: " + recibido2);
                    int numero2 = Integer.parseInt(recibido2);
                    
                    // PASO 3: Manda el menú y recibe la opción elegida
                    toClient.println("1.Suma | 2.Resta | 3.Multiplicacion | 4.Division - Introduzca la operacion:");
                    String recibido3 = fromClient.readLine();
                    System.out.println("El cliente eligio la operacion: " + recibido3);
                    
                    // PASO 4: Calcula y envía el resultado
                    String resultadoFinal = calcularResultado(numero1, numero2, recibido3);
                    toClient.println(resultadoFinal);

                } catch (NumberFormatException e) {
                    toClient.println("Error: Por favor, ingrese numeros validos.");
                }

                // 2. CERRAR la conexión del cliente al terminar la interacción
                client.close();
                System.out.println("Interaccion finalizada. Cliente desconectado.");
            }

        } catch (IOException ex) {
            System.out.print("Error de Servidor: " + ex.getMessage());
        }
    }

    // Método extraído para mantener el main limpio y manejar errores (como división por cero)
    private static String calcularResultado(int numero1, int numero2, String operacion) {
        int resultado = 0;
        
        // Limpiamos la entrada por si el usuario pone espacios extra
        switch (operacion.trim().toLowerCase()) {
            case "1":
            case "suma":
            case "sumar":
                resultado = numero1 + numero2;
                break;
            case "2":
            case "resta":
            case "restar":
                resultado = numero1 - numero2;
                break;
            case "3":
            case "multiplicacion":
            case "multiplicar":
                resultado = numero1 * numero2;
                break;
            case "4":
            case "division":
            case "dividir":
                if (numero2 == 0) {
                    return "Error: No se puede dividir entre cero.";
                }
                resultado = numero1 / numero2;
                break;
            default:
                return "Error: Operacion no reconocida.";
        }
        return String.valueOf(resultado);
    }
}