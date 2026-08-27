/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicas2.soquetsrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author usuario
 */
public interface IServidorJusticia extends Remote {

    /**
     * Consulta en ambos bancos (Mercantil por TCP, BCP por UDP) las cuentas
     * asociadas a una persona.
     * @param ci
     * @param nombres
     * @param apellidos
     * @return 
     * @throws java.rmi.RemoteException
     */
    RespuestaCuenta ConsultarCuentas(String ci, String nombres, String apellidos) throws RemoteException;

    /**
     * Ordena el congelamiento de una cuenta especifica en el banco indicado.
     * @param nrocuenta
     * @param banco
     * @param monto
     * @return 
     * @throws java.rmi.RemoteException
     */
    RespuestaCuenta Congelar(String nrocuenta, Banco banco, Double monto) throws RemoteException;
}

