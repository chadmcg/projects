/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.daos.OrderDao;
import com.mycompany.flooringmastery.daos.OrderDaoException;
import com.mycompany.flooringmastery.dtos.Order;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Chad
 */
public class InMemOrderDao implements OrderDao {
    
    Order order1;
    Order order2;
    Order order3;
    
    List<Order> orderList = new ArrayList<>();
    
    public InMemOrderDao(){
        
        order1 = new Order();
        order1.setOrderNumber(1);
        order1.setOrderDate(LocalDate.of(2020,1,1));
        order1.setCustomerName("TEST CUST 1");
        order1.setState("MN");
        order1.setTaxRate(new BigDecimal("6.25"));
        order1.setProductType("TEST PROD 1");
        order1.setArea(new BigDecimal("100.00"));
        order1.setMatCostPerSquareFoot(new BigDecimal("1.25"));
        order1.setLaborCostPerSquareFoot(new BigDecimal("3.25"));
        orderList.add(order1);
        
        order2 = new Order();
        order2.setOrderNumber(2);
        order2.setOrderDate(LocalDate.of(2020,2,1));
        order2.setCustomerName("TEST CUST 2");
        order2.setState("WI");
        order2.setTaxRate(new BigDecimal("7.25"));
        order2.setProductType("TEST PROD 2");
        order2.setArea(new BigDecimal("200.00"));
        order2.setMatCostPerSquareFoot(new BigDecimal("2.25"));
        order2.setLaborCostPerSquareFoot(new BigDecimal("4.25"));
        orderList.add(order2);    
        
        order3 = new Order();
        order3.setOrderNumber(3);
        order3.setOrderDate(LocalDate.of(2020,1,1));
        order3.setCustomerName("TEST CUST 3");
        order3.setState("OH");
        order3.setTaxRate(new BigDecimal("8.25"));
        order3.setProductType("TEST PROD 3");
        order3.setArea(new BigDecimal("300.00"));
        order3.setMatCostPerSquareFoot(new BigDecimal("3.25"));
        order3.setLaborCostPerSquareFoot(new BigDecimal("5.25"));
        orderList.add(order3);  
        
    }

    @Override
    public List<Order> getOrders(LocalDate orderDate) throws OrderDaoException {
        
        List<Order> ordersToReturn = new ArrayList<>();
        
        for(Order toTest : orderList){
            
            if(toTest.getOrderDate().equals(orderDate)){
                ordersToReturn.add(toTest);
            }
            
        }
        
        return ordersToReturn;

    }


    @Override
    public void addNewOrder(Order newOrder) throws OrderDaoException {

        LocalDate orderDate = newOrder.getOrderDate();

        List<Order> allOrders = getOrders(orderDate);
        
         int maxOrderNumber = 0;

        if (allOrders.size() > 0) {

            for (Order orderEntry : allOrders) {

                if (orderEntry.getOrderNumber() > maxOrderNumber) {

                    maxOrderNumber = orderEntry.getOrderNumber();

                }

            }
        }

        maxOrderNumber++;
        newOrder.setOrderNumber(maxOrderNumber);
        
        
        
        
        allOrders.add(newOrder);

        writeData(allOrders, orderDate);

    }

    private void writeData(List<Order> allOrders, LocalDate orderDate ) throws OrderDaoException {
        
        String orderDateString = generatePartialFileName(orderDate);

        String filePath = generateFileName(orderDate);

        FileWriter writer = null;

        try {
            writer = new FileWriter(filePath);

        } catch (IOException ex) {
            throw new OrderDaoException("Error: Could not write to " + filePath);
        }

        PrintWriter pw = new PrintWriter(writer);
        pw.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

        for (Order toWrite : allOrders) {

            String line = convertToLine(toWrite);

            pw.println(line);
        }

        try {
            writer.close();
        } catch (IOException ex) {
            throw new OrderDaoException("Error: Could not close writer for " + filePath);
        }

    }

    private String convertToLine(Order toWrite) {
        String line
                = toWrite.getOrderNumber() + ","
                + toWrite.getCustomerName().toUpperCase() + ","
                + toWrite.getState().toUpperCase() + ","
                + toWrite.getTaxRate() + ","
                + toWrite.getProductType().toUpperCase() + ","
                + toWrite.getArea() + ","
                + toWrite.getMatCostPerSquareFoot() + ","
                + toWrite.getLaborCostPerSquareFoot() + ","
                + toWrite.getTotalMatCost() + ","
                + toWrite.getTotalLaborCost() + ","
                + toWrite.getTotalTax() + ","
                + toWrite.getTotalCost();

        return line;
    }

    @Override
    public void delete(LocalDate orderDate, int orderNum) throws OrderDaoException{

 List<Order> ordersByDay = getOrders(orderDate);

        for (Order orderToCheck : ordersByDay) {

            if (orderToCheck.getOrderNumber() == orderNum) {

                ordersByDay.remove(orderToCheck);
                break;
            }

        }

        

        if (ordersByDay.size() > 0) {
            writeData(ordersByDay, orderDate);
        } else {
            deleteFile(orderDate);
        }
        
    

    }

    private String generatePartialFileName(LocalDate orderDate) {
       String orderDateString = orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));
       
       return orderDateString;
    }

    @Override
    public void edit(Order editedOrder) throws OrderDaoException {
      //Get all orders for date
        List<Order> allOrdersForDate = getOrders(editedOrder.getOrderDate());

        //Find the original order that has the same id as the edited order
        int orderIndex = -1;
        
        for(int i = 0; i <allOrdersForDate.size(); i++){
            
            Order toCheck = allOrdersForDate.get(i);
            
            if(toCheck.getOrderNumber()==editedOrder.getOrderNumber()){
                
                orderIndex = i;
                break;
            }
            
        }
        
        //Remove the original order
        allOrdersForDate.remove(orderIndex);
        
        //Add the edited order
        allOrdersForDate.add(editedOrder);

        //Write to file
        writeData(allOrdersForDate, editedOrder.getOrderDate());

    }

    private void deleteFile(LocalDate orderDate) {
        Paths.get(generateFileName(orderDate)).toFile().delete();
        
    }

    private String generateFileName(LocalDate orderDate) {
        return Paths.get("OrderTestFiles", "OrdersTest_" + this.generatePartialFileName(orderDate) + ".txt").toString();
    }

    

    
    
}
