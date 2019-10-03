/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.service;

/**
 *
 * @author Chad
 */
public class VendingMachineServiceLayerException extends Exception {
    
    public VendingMachineServiceLayerException(String message) {

        super(message);

    }

    public VendingMachineServiceLayerException (String message, Throwable cause) {

        super(message, cause);
    
}

}