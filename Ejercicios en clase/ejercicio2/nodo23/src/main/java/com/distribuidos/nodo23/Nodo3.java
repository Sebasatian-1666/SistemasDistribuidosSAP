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

public class Nodo3 {

    static int PUERTO_PROPIO = 6003;
    static int PUERTO_NODO1 = 6001;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(PUERTO_PROPIO);
            System.out.println("Nodo 3 esperando datos...");

            byte[] bufer = new byte[2000];
            DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);
            socket.receive(peticion);

            String recibido = new String(peticion.getData(), 0, peticion.getLength());
            System.out.println("Recibido del Nodo 2: " + recibido);

            // Parseamos toda la información acumulada
            String[] partes = recibido.split("\\|");
            String texto = partes[0];
            int cantidadCaracteres = Integer.parseInt(partes[1]);
            int cantidadPalabras = Integer.parseInt(partes[2]);
            String paridad = partes[3];

            // Procesamos: mayúsculas y vocales
            String textoMayusculas = texto.toUpperCase();
            int cantidadVocales = contarVocales(texto);

            // Generamos el resumen final
            String resumen =
                "Texto original: " + texto + "\n" +
                "Texto en mayúsculas: " + textoMayusculas + "\n" +
                "Cantidad de caracteres: " + cantidadCaracteres + " (" + paridad + ")\n" +
                "Cantidad de palabras: " + cantidadPalabras + "\n" +
                "Cantidad de vocales: " + cantidadVocales;
            InetAddress ip1 = InetAddress.getByName("10.216.58.48");

            DatagramPacket respuesta = new DatagramPacket(
                resumen.getBytes(), resumen.getBytes().length,
                ip1, PUERTO_NODO1);
            socket.send(respuesta);

            System.out.println("Enviado al Nodo 1:\n" + resumen);
            socket.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static int contarVocales(String texto) {
        int contador = 0;
        String vocales = "aeiouAEIOUáéíóúÁÉÍÓÚ";
        for (char c : texto.toCharArray()) {
            if (vocales.indexOf(c) != -1) {
                contador++;
            }
        }
        return contador;
    }
}
