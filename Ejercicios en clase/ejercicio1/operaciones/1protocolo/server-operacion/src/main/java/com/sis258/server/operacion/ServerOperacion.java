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
            // El servidor se inicia UNA sola vez afuera del ciclo while
            server = new ServerSocket(port);
            System.out.println("Se inicio el servidor con éxito en el puerto: " + port);
            
            while (true) {
                Socket client;
                PrintStream toClient;
                
                // El programa se detiene aquí hasta que un cliente se conecta
                client = server.accept(); 
                System.out.println("Cliente se conecto");
                
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String recibido = fromClient.readLine();
                System.out.println("El cliente envio el mensaje: " + recibido);
                
                toClient = new PrintStream(client.getOutputStream());
                
                // Procesamos la cadena según su protocolo
                String respuesta = procesarSolicitud(recibido);
                toClient.println(respuesta);
                
                // Cerramos la conexión con ESTE cliente para liberar recursos
                client.close();
                System.out.println("Conexión con el cliente cerrada con éxito.\n");
            }

        } catch (IOException ex) {
            System.out.print("Error en el servidor: " + ex.getMessage());
        }
    }
    
    public static String procesarSolicitud(String cadena) {
        try {
            // Dividimos la cadena usando el separador de su protocolo ":"
            String[] partes = cadena.split(":");
            String operacion = partes[0].toLowerCase();
            int num1 = Integer.parseInt(partes[1]);
            int num2 = Integer.parseInt(partes[2]);
            int resultado = 0;
            
            switch (operacion) {
                case "suma":
                case "sumar":
                    resultado = num1 + num2;
                    break;
                case "resta":
                case "restar":
                    resultado = num1 - num2;
                    break;
                case "multiplicacion":
                case "multiplicar":
                    resultado = num1 * num2;
                    break;
                case "division":
                case "dividir":
                    // Protección básica anti-crasheos por división entre cero
                    if (num2 == 0) {
                        return "Error: No se puede dividir entre cero";
                    }
                    resultado = num1 / num2;
                    break;
                default:
                    return "Error: Operacion no reconocida";
            }
            return String.valueOf(resultado);
            
        } catch (Exception e) {
            // Si el cliente manda texto normal en vez del protocolo estructurado
            return "Error: Formato de protocolo incorrecto. Use operacion:num1:num2";
        }
    }
}