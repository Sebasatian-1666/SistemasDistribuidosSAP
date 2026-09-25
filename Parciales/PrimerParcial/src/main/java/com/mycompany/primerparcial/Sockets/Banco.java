/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primerparcial.Sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class Banco { 
  public Banco() throws IOException{
      try {
          double monto = 0;
          String pasporte = null;
          double limite;
          switch(pasporte){
              case "BOL-240376":
                  limite =2000.0;
                  break;
              case "ARG´-409078":
                  limite =150;
                  break;
              default:
                  limite =0;
                  break;
          }
          if(monto >= limite){
              System.out.println("Saldo Insuficiente");
          }
          String emo = ""+monto;
          DatagramSocket s =
                  new DatagramSocket();
          byte[] m;
          m = emo.getBytes();
          InetAddress h = null;
          try {
              h = InetAddress.getByName("localhost");
          } catch (UnknownHostException ex) {
              Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
          }
          s.send(new DatagramPacket(m,m.length,h,6789));
          byte[] buf = new byte[1000];
          DatagramPacket r = new DatagramPacket(buf, buf.length);
          s.receive(r);
          System.out.println(new String(r.getData(),0,r.getLength()));
          s.close();
      } catch (SocketException ex) {
          Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
      }
  } 
}
