/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.service;

/**
 *
 * @author Chad
 */
public class OrderNumNotFoundException extends Exception {
    
    public OrderNumNotFoundException(String message) {

        super(message);

    }

    public OrderNumNotFoundException(String message, Throwable cause) {

        super(message, cause);
    }
    
    
    
}
