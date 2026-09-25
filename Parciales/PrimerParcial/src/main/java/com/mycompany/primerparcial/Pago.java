/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primerparcial;

/**
 *
 * @author USUARIO
 */
public class Pago {
    boolean aprobado;
    String codigoAutorizacion;
    String motivo;

    public Pago(boolean aprobado, String codigoAutorizacion, String motivo) {
        this.aprobado = aprobado;
        this.codigoAutorizacion = codigoAutorizacion;
        this.motivo = motivo;
    }
    
}
