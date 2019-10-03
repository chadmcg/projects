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
public class OrderNotFoundForDateException extends Exception {
    
    public OrderNotFoundForDateException(String message) {

        super(message);

    }

    public OrderNotFoundForDateException(String message, Throwable cause) {

        super(message, cause);
    }
    
}
