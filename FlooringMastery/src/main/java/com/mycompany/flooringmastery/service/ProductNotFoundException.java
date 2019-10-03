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
public class ProductNotFoundException extends Exception {
    
    public ProductNotFoundException(String message) {

        super(message);

    }

    public ProductNotFoundException(String message, Throwable cause) {

        super(message, cause);
    }
    
}
