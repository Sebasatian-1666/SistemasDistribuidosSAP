/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primerparcial;

/**
 *
 * @author USUARIO
 */
public class Voucher {
    boolean confirmado;
    String codigoCompra;
    String motivo;
    double montoUSD;

    public boolean isConfirmado() {
        return confirmado;
    }

    public String getCodigoCompra() {
        return codigoCompra;
    }

    public String getMotivo() {
        return motivo;
    }

    public double getMontoUSD() {
        return montoUSD;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public void setCodigoCompra(String codigoCompra) {
        this.codigoCompra = codigoCompra;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setMontoUSD(double montoUSD) {
        this.montoUSD = montoUSD;
    }
    
}
