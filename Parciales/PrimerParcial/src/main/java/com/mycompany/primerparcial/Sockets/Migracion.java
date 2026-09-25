/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primerparcial.Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class Migracion {
   public Migracion(){
       try {
           int port = 5002;
           ServerSocket server = new ServerSocket(port);
           Socket client = server.accept();
           BufferedReader fromClient = new BufferedReader(
                   new InputStreamReader(client.getInputStream()));
           PrintStream toClient =
                   new PrintStream(client.getOutputStream());
           String recibido = fromClient.readLine();
           String  resultado = null;
           switch (recibido){
               case "BOL-240376":
                   resultado = "APROBADO";
                   break;
               default:
                   resultado = "Pasaporte Inválido";
                   break;
           }
           toClient.println(resultado);
       } catch (IOException ex) {
           Logger.getLogger(Migracion.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}
