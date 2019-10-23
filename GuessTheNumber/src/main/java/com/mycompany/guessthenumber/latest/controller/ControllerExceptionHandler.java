/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumber.latest.controller;

import com.mycompany.guessthenumber.latest.service.GameNotFoundException;
import com.mycompany.guessthenumber.latest.service.InvalidGuessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Chad
 */

@ControllerAdvice
@RestController
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(GameNotFoundException.class)
    public final ResponseEntity<Error> handleGameNotFoundException(
            GameNotFoundException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage("No game was found for the supplied id.");
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    } 
    
    @ExceptionHandler(InvalidGuessException.class)
    public final ResponseEntity<Error> handleInvalidGuessException(
            InvalidGuessException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage("The guess that was supplied is invalid.");
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    
}
