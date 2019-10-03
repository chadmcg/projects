/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

/**
 *
 * @author Chad
 */
public class ProductsDaoException extends Exception {
    
    public ProductsDaoException(String message) {

        super(message);

    }

    public ProductsDaoException(String message, Throwable cause) {

        super(message, cause);
    }
    
}
