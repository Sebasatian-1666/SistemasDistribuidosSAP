/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primerparcial.Sockets;

import com.mycompany.primerparcial.Voucher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class ServidorOperadora {
   public ServidorOperadora(){
       String pasporte = "BOL-240376";
       try {
           Socket client = new Socket("localhost", 5002);
           PrintStream toServer=
                   new PrintStream(client.getOutputStream());
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
           toServer.println(pasporte);
           String result = fromServer.readLine();
           System.out.println("Estado: " + result);
           client.close();
       } catch (IOException ex) {
           Logger.getLogger(ServidorOperadora.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}
