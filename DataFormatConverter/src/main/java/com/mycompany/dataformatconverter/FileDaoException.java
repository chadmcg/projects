/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataformatconverter;

/**
 *
 * @author Chad
 */
public class FileDaoException extends Exception  {
    
      public FileDaoException(String message){
        
        super(message);
        
    }
    
    public FileDaoException(String message, Throwable cause){
        
        super(message, cause);
    }
    
    
}
