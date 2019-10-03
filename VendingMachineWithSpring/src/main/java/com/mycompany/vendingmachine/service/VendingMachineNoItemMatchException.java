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
public class VendingMachineNoItemMatchException extends Exception{
    
    public VendingMachineNoItemMatchException(String message) {

        super(message);

    }

    public VendingMachineNoItemMatchException (String message, Throwable cause) {

        super(message, cause);
    }
    
}
