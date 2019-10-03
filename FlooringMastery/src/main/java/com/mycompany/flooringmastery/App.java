/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery;

import com.mycompany.flooringmastery.controller.Controller;
import com.mycompany.flooringmastery.daos.OrderDaoException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Chad
 */
public class App {

    public static void main(String[] args) throws OrderDaoException {


        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        Controller myController
                = ctx.getBean("controller", Controller.class);
        myController.runProgram();
                

    }

}
