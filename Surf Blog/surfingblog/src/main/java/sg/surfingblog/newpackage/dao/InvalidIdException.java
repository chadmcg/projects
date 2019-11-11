/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.surfingblog.newpackage.dao;

/**
 *
 * @author blee0
 */
public class InvalidIdException extends Exception {

    public InvalidIdException(String message) {
        super(message);
    }

    public InvalidIdException(String message, Throwable inner) {
        super(message, inner);
    }
    
}
