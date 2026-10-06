/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.arquitectura.tarea1;
import java.io.Serializable;
/**
 *
 * @author usuario
 */
public class MensajeAforo implements Serializable {
    // Requisito para clases serializables
    private static final long serialVersionUID = 1L;
    
    // Tipos de mensaje definidos en los requisitos
    public enum Tipo { SOLICITUD, ACEPTADO, RECHAZADO, SALIDA }

    private Tipo tipo;
    private String puerta;
    private int personas;

    public MensajeAforo(Tipo tipo, String puerta, int personas) {
        this.tipo = tipo;
        this.puerta = puerta;
        this.personas = personas;
    }

    public Tipo getTipo() { return tipo; }
    public String getPuerta() { return puerta; }
    public int getPersonas() { return personas; }
}