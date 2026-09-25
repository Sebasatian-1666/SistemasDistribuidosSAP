/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.primerparcial;

import java.rmi.Remote;

/**
 *
 * @author USUARIO
 */
public interface Remota extends Remote{
   public Voucher ComprarTour(String Pasporte, String codigoTour, int personas);
}
