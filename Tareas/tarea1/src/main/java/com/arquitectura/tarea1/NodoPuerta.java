/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.arquitectura.tarea1;

import org.jgroups.*;
import org.jgroups.util.Util;
import java.io.*;
/**
 *
 * @author usuario
 */
public class NodoPuerta implements Receiver {
    private JChannel canal;
    private String nombre;
    private int aforoMaximo;
    private int ocupacionActual = 0;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso correcto: java NodoPuerta  ");
            return;
        }
        try {
            NodoPuerta nodo = new NodoPuerta();
            nodo.iniciar(args[0], Integer.parseInt(args[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iniciar(String nombre, int aforoMaximo) throws Exception {
        this.nombre = nombre;
        this.aforoMaximo = aforoMaximo;

        canal = new JChannel();          
        canal.name(nombre);              
        canal.setReceiver(this);         
        canal.connect("AforoSIS258");    
        
        canal.getState(null, 10000);   

        System.out.println("Sistema de Aforo Parque Cretácico iniciado.");
        leerTeclado();
        
        canal.close(); 
    }

    private void leerTeclado() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Comandos: /entrar n, /salir n, /estado");

        while (true) {
            System.out.print("> ");
            String linea = in.readLine();
            if (linea == null) break;
            
            String[] partes = linea.trim().split(" "); 

            if (linea.startsWith("/entrar")) {
                int n = Integer.parseInt(partes[1]);
                Address coordinador = canal.getView().getCoord(); 
                
                canal.send(new ObjectMessage(coordinador, 
                           new MensajeAforo(MensajeAforo.Tipo.SOLICITUD, nombre, n)));
            }
            else if (linea.startsWith("/salir")) {
                int n = Integer.parseInt(partes[1]);
                
                canal.send(new ObjectMessage(null, 
                           new MensajeAforo(MensajeAforo.Tipo.SALIDA, nombre, n)));
            }
            else if (linea.equals("/estado")) {
                System.out.println("Ocupacion: " + ocupacionActual + "/" + aforoMaximo);
                if (ocupacionActual >= aforoMaximo) {
                    System.out.println("AFORO COMPLETO");
                }
            }
        }
    }

    @Override
    public void receive(Message msg) {
        MensajeAforo m = msg.getObject(); 

        switch (m.getTipo()) {
            case SOLICITUD:
                boolean soyCoordinador = canal.getAddress().equals(canal.getView().getCoord());
                if (soyCoordinador) {
                    try {
                        if (ocupacionActual + m.getPersonas() <= aforoMaximo) {
                            canal.send(new ObjectMessage(null, 
                                       new MensajeAforo(MensajeAforo.Tipo.ACEPTADO, m.getPuerta(), m.getPersonas())));
                        } else {
                            canal.send(new ObjectMessage(msg.getSrc(), 
                                       new MensajeAforo(MensajeAforo.Tipo.RECHAZADO, m.getPuerta(), m.getPersonas())));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case ACEPTADO:
                ocupacionActual += m.getPersonas();
                System.out.println("\n[AVISO] Entraron " + m.getPersonas() + " personas por la " + m.getPuerta());
                System.out.print("> ");
                break;

            case RECHAZADO:
                System.out.println("\n[DENEGADO] Aforo insuficiente para ingresar " + m.getPersonas() + " personas en la " + m.getPuerta());
                System.out.print("> ");
                break;

            case SALIDA:
                ocupacionActual -= m.getPersonas();
                if(ocupacionActual < 0) ocupacionActual = 0;
                System.out.println("\n[AVISO] Salieron " + m.getPersonas() + " personas por la " + m.getPuerta());
                System.out.print("> ");
                break;
        }
    }

    @Override
    public void viewAccepted(View vista) {
        System.out.println("\n--- CAMBIO EN LA RED ---");
        System.out.println("Nodos conectados: " + vista.getMembers());
        System.out.println("Coordinador actual: " + vista.getCoord());
        System.out.println("------------------------");
        System.out.print("> ");
    }

    @Override
    public void getState(OutputStream out) throws Exception {
        Util.objectToStream(ocupacionActual, new DataOutputStream(out));
    }

    @Override
    public void setState(InputStream in) throws Exception {
        ocupacionActual = (Integer) Util.objectFromStream(new DataInputStream(in));
        System.out.println("\n[SINCRONIZADO] La ocupación actual de la red es: " + ocupacionActual);
        System.out.print("> ");
    }
}