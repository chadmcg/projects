/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine;

import com.mycompany.vendingmachine.controller.VendingMachineController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Chad
 */
public class App {

    public static void main(String[] args) {

//      //Pre-spring app set-up
//      UserIO myIO = new UserIOConsoleImpl();
//      VendingMachineView myView = new VendingMachineView(myIO);
//      VendingMachineDao myDao = new VendingMachineDaoFileImpl("Inventory.txt");
//      VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao);
//      VendingMachineController newController = new VendingMachineController(myView, myService);
//     
//      newController.runProgram();

        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller
                = ctx.getBean("controller", VendingMachineController.class);
        controller.runProgram();

    }

}
