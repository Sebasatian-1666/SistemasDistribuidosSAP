/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.distribuidos.nodo23;

/**
 *
 * @author usuario
 */
import java.net.*;
import java.io.*;

public class Nodo2 {

    static int PUERTO_PROPIO = 6002;
    static int PUERTO_NODO3 = 6003;
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(PUERTO_PROPIO);
            System.out.println("Nodo 2 esperando datos...");

            byte[] bufer = new byte[2000];
            DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);
            socket.receive(peticion);

            String recibido = new String(peticion.getData(), 0, peticion.getLength());
            System.out.println("Recibido del Nodo 1: " + recibido);

            // Parseamos texto y cantidad de caracteres
            String[] partes = recibido.split("\\|");
            String texto = partes[0];
            int cantidadCaracteres = Integer.parseInt(partes[1]);

            // Procesamos: cantidad de palabras y paridad
            int cantidadPalabras = texto.trim().split("\\s+").length;
            String paridad = (cantidadCaracteres % 2 == 0) ? "PAR" : "IMPAR";

            // Agregamos los resultados al mensaje
            String mensaje = texto + "|" + cantidadCaracteres + "|" + cantidadPalabras + "|" + paridad;
            InetAddress ip3 = InetAddress.getByName("localhost");

            DatagramPacket respuesta = new DatagramPacket(
                mensaje.getBytes(), mensaje.getBytes().length,
                    ip3, PUERTO_NODO3);
            socket.send(respuesta);
            
            System.out.println("Enviado al Nodo 3: " + mensaje);
            socket.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
