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
public class TaxesDaoException extends Exception {
    
    public TaxesDaoException(String message) {

        super(message);

    }

    public TaxesDaoException(String message, Throwable cause) {

        super(message, cause);
    }
    
}
