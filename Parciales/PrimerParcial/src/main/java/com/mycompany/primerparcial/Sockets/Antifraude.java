/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primerparcial.Sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
class Antifraude {
    public Antifraude(){
        try {
           DatagramSocket s = new DatagramSocket(6789);
           byte[] buf = new byte[1000];
           while(true){

               DatagramPacket p =
                       new DatagramPacket(buf, buf.length);
               s.receive(p);
               String c = new String(
                       p.getData(),0,p.getLength());
               double x = Double.parseDouble(c.trim());
               String t;
               if (x <=1000.0){
                   t="Riesgo Bajo";
               }else{
                   t="Riesgo Alto";
               }
               byte m[] = (t).getBytes();
               DatagramPacket r =
                       new DatagramPacket(m,m.length,p.getAddress(), p.getPort());
               s.send(r);
           }
       } catch (IOException ex) {
           Logger.getLogger(Antifraude.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
}
