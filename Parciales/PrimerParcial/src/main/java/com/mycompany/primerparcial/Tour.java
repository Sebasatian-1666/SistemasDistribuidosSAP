/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primerparcial;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author USUARIO
 */
public class Tour extends UnicastRemoteObject implements Remota {
    public Tour()throws RemoteException{super();}
    @Override
    public Voucher ComprarTour(String Pasporte, String codigoTour, int personas) {
       return new Voucher();
    }
    
}
