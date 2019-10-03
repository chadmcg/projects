/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.dtos.Order;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chad
 */
public class OrderDaoFileImpl implements OrderDao {

    String fileFolder;
    String fileName;

    public OrderDaoFileImpl(String fileFolder, String fileName) {

        this.fileFolder = fileFolder;
        this.fileName = fileName;

    }

    @Override
    public List<Order> getOrders(LocalDate orderDate) throws OrderDaoException {

       String orderDateString = generatePartialFileName(orderDate);
        
        String filePath = Paths.get(fileFolder, fileName + orderDateString + ".txt").toString();

        List<Order> ordersToReturn = new ArrayList<>();

        FileReader reader = null;

        try {
            reader = new FileReader(filePath);
            Scanner newScn = new Scanner(reader);
            newScn.nextLine();

            while (newScn.hasNextLine()) {

                String line = newScn.nextLine();
                if (line.length() > 0) {

                    String[] fields = line.split(",");

                    Order toAdd = new Order();

                    toAdd.setOrderNumber(Integer.parseInt(fields[0]));
                    toAdd.setCustomerName(fields[1]);
                    toAdd.setState(fields[2]);
                    toAdd.setTaxRate(new BigDecimal(fields[3]));
                    toAdd.setProductType(fields[4]);
                    toAdd.setArea(new BigDecimal(fields[5]));
                    toAdd.setMatCostPerSquareFoot(new BigDecimal(fields[6]));
                    toAdd.setLaborCostPerSquareFoot(new BigDecimal(fields[7]));
                    toAdd.setOrderDate(LocalDate.parse(orderDateString, DateTimeFormatter.ofPattern("MMddyyyy")));

                    ordersToReturn.add(toAdd);

                }

            }

        } catch (FileNotFoundException ex) {
            return ordersToReturn;
        } finally {
            try {
                if (reader != null) {

                    reader.close();

                }
            } catch (IOException ex) {
                throw new OrderDaoException("Error: Could not closer writer.");
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
        return Paths.get(fileFolder, fileName + this.generatePartialFileName(orderDate) + ".txt").toString();
    }

    

}
