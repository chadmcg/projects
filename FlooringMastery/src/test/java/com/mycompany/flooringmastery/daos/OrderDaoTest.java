/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.dtos.Order;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Chad
 */
public class OrderDaoTest {

    public OrderDaoTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {

        Path orderTestPath = Paths.get("OrderTestFiles");
        Path orderSeedPath = Paths.get("OrderSeedFiles");

        File orderTestFolder = orderTestPath.toFile();
        File orderSeedFolder = orderSeedPath.toFile();

        if (!orderTestFolder.exists()) {

            orderTestFolder.mkdir();
        }

        File[] orderTestFiles = orderTestFolder.listFiles();

        for (File orderTestFile : orderTestFiles) {

            orderTestFile.delete();

        }

        File[] orderSeedFiles = orderSeedFolder.listFiles();

        for (File orderSeedFile : orderSeedFiles) {

            Files.copy(orderSeedFile.toPath(), Paths.get(orderTestPath.toString(), orderSeedFile.getName()), StandardCopyOption.REPLACE_EXISTING);

        }

    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getOrders method, of class OrderDao.
     */
    @Test
    public void testGetOrders() {

        try {
            //Initialize
            OrderDao toTest = new OrderDaoFileImpl("OrderTestFiles", "OrdersTest_");
            List<Order> allOrderInfo = toTest.getOrders(LocalDate.of(2020,1,1));

            //There are 2 orders in the 01/01/2020 test file
            //Verify that the correct number of records is returned
            assertEquals(2, allOrderInfo.size());

            //Verify that the data returned for the first order is correct
            String toCheckOrderNumber = "Placeholder";
            String toCheckName = "Placeholder";
            String toCheckState = "Placeholder";
            BigDecimal toCheckTaxRate = BigDecimal.ZERO;
            String toCheckProduct = "Placeholder";
            BigDecimal toCheckArea = BigDecimal.ZERO;
            BigDecimal toCheckUnitMatCost = BigDecimal.ZERO;
            BigDecimal toCheckUnitLaborCost = BigDecimal.ZERO;
            BigDecimal toCheckTotalMatCost = BigDecimal.ZERO;
            BigDecimal toCheckTotalLaborCost = BigDecimal.ZERO;
            BigDecimal toCheckTotalTax = BigDecimal.ZERO;
            BigDecimal toCheckTotal = BigDecimal.ZERO;

            for (Order toCheck : allOrderInfo) {
                if (toCheck.getOrderNumber() == 1) {
                    toCheckName = toCheck.getCustomerName();
                    toCheckState = toCheck.getState();
                    toCheckTaxRate = toCheck.getTaxRate();
                    toCheckProduct = toCheck.getProductType();
                    toCheckArea = toCheck.getArea();
                    toCheckUnitMatCost = toCheck.getMatCostPerSquareFoot();
                    toCheckUnitLaborCost = toCheck.getLaborCostPerSquareFoot();
                    toCheckTotalMatCost = toCheck.getTotalMatCost();
                    toCheckTotalLaborCost = toCheck.getTotalLaborCost();
                    toCheckTotalTax = toCheck.getTotalTax();
                    toCheckTotal = toCheck.getTotalCost();
                }
            }

            assertEquals("TEST FILE 1 NAME 1", toCheckName);
            assertEquals("OH", toCheckState);
            assertEquals(new BigDecimal("6.25"), toCheckTaxRate);
            assertEquals("WOOD", toCheckProduct);
            assertEquals(new BigDecimal("100.00"), toCheckArea);
            assertEquals(new BigDecimal("5.15"), toCheckUnitMatCost);
            assertEquals(new BigDecimal("4.75"), toCheckUnitLaborCost);

            //Calculated fields (through getters); not actually being read from the file
            assertEquals(new BigDecimal("515.00"), toCheckTotalMatCost);
            assertEquals(new BigDecimal("475.00"), toCheckTotalLaborCost);
            assertEquals(new BigDecimal("61.88"), toCheckTotalTax);
            assertEquals(new BigDecimal("1051.88"), toCheckTotal);

            //Verify that the data returned for the last record is correct;
            for (Order toCheck : allOrderInfo) {
                if (toCheck.getOrderNumber() == 2) {
                    toCheckName = toCheck.getCustomerName();
                    toCheckState = toCheck.getState();
                    toCheckTaxRate = toCheck.getTaxRate();
                    toCheckProduct = toCheck.getProductType();
                    toCheckArea = toCheck.getArea();
                    toCheckUnitMatCost = toCheck.getMatCostPerSquareFoot();
                    toCheckUnitLaborCost = toCheck.getLaborCostPerSquareFoot();
                    toCheckTotalMatCost = toCheck.getTotalMatCost();
                    toCheckTotalLaborCost = toCheck.getTotalLaborCost();
                    toCheckTotalTax = toCheck.getTotalTax();
                    toCheckTotal = toCheck.getTotalCost();
                }
            }

            assertEquals("TEST FILE 1 NAME 2", toCheckName);
            assertEquals("MN", toCheckState);
            assertEquals(new BigDecimal("8.25"), toCheckTaxRate);
            assertEquals("CARPET", toCheckProduct);
            assertEquals(new BigDecimal("300.00"), toCheckArea);
            assertEquals(new BigDecimal("5.15"), toCheckUnitMatCost);
            assertEquals(new BigDecimal("7.75"), toCheckUnitLaborCost);

            //Calculated fields (through getters); not actually being read from the file
            assertEquals(new BigDecimal("1545.00"), toCheckTotalMatCost);
            assertEquals(new BigDecimal("2325.00"), toCheckTotalLaborCost);
            assertEquals(new BigDecimal("319.28"), toCheckTotalTax);
            assertEquals(new BigDecimal("4189.28"), toCheckTotal);

        } catch (OrderDaoException ex) {
            fail("An exception is not expected.");
        }
    }

    @Test
    public void testGetOrdersNoFile() {

        try {
            //Initialize
            //There is not a test file for the date below
            OrderDao toTest = new OrderDaoFileImpl("OrderTestFiles", "OrdersTest_");
            List<Order> allOrderInfo = toTest.getOrders(LocalDate.of(2020,12,31));

            //Verify that a list of size zero is returned
            assertEquals(0, allOrderInfo.size());
            
        } catch (OrderDaoException ex) {
            fail("An exception is not expected.");
        }

    }


    @Test
    public void testAddNewOrderExistingFile() {

        try {
            //Initialize
            OrderDao toTest = new OrderDaoFileImpl("OrderTestFiles", "OrdersTest_");

            //Get existing orders from 02/01/2020 file
            List<Order> allOrderInfo = toTest.getOrders(LocalDate.of(2020,2,1));

            //Verify that the 02/01/2020 file has two orders
            assertEquals(2, allOrderInfo.size());

            //Create new order
            Order testOrder = new Order();
            testOrder.setOrderDate(LocalDate.parse("02/01/2020", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            testOrder.setOrderNumber(3);
            testOrder.setCustomerName("TEST FILE 2 NAME 3");
            testOrder.setState("IN");
            testOrder.setTaxRate(new BigDecimal("7.25"));
            testOrder.setProductType("TILE");
            testOrder.setArea(new BigDecimal("200.00"));
            testOrder.setMatCostPerSquareFoot(new BigDecimal("5.15"));
            testOrder.setLaborCostPerSquareFoot(new BigDecimal("6.75"));

            //write new order to the 02/01/2020 file
            toTest.addNewOrder(testOrder);

            //Get existing orders from 02/01/2020 file
            allOrderInfo = toTest.getOrders(LocalDate.of(2020,2,1));

            //Verify that the 02/01/2020 file now has three orders
            assertEquals(3, allOrderInfo.size());

            //Verify that the data in the first row of the updated 02/01/2020 file is correct
            String toCheckOrderNumber = "Placeholder";
            String toCheckName = "Placeholder";
            String toCheckState = "Placeholder";
            BigDecimal toCheckTaxRate = BigDecimal.ZERO;
            String toCheckProduct = "Placeholder";
            BigDecimal toCheckArea = BigDecimal.ZERO;
            BigDecimal toCheckUnitMatCost = BigDecimal.ZERO;
            BigDecimal toCheckUnitLaborCost = BigDecimal.ZERO;

            for (Order toCheck : allOrderInfo) {
                if (toCheck.getOrderNumber() == 1) {
                    toCheckName = toCheck.getCustomerName();
                    toCheckState = toCheck.getState();
                    toCheckTaxRate = toCheck.getTaxRate();
                    toCheckProduct = toCheck.getProductType();
                    toCheckArea = toCheck.getArea();
                    toCheckUnitMatCost = toCheck.getMatCostPerSquareFoot();
                    toCheckUnitLaborCost = toCheck.getLaborCostPerSquareFoot();

                }
            }

            assertEquals("TEST FILE 2 NAME 1", toCheckName);
            assertEquals("OH", toCheckState);
            assertEquals(new BigDecimal("6.25"), toCheckTaxRate);
            assertEquals("WOOD", toCheckProduct);
            assertEquals(new BigDecimal("100.00"), toCheckArea);
            assertEquals(new BigDecimal("5.15"), toCheckUnitMatCost);
            assertEquals(new BigDecimal("4.75"), toCheckUnitLaborCost);

            //Verify that the data in the last row of the updated 02/01/2020 file is correct
            for (Order toCheck : allOrderInfo) {
                if (toCheck.getOrderNumber() == 3) {
                    toCheckName = toCheck.getCustomerName();
                    toCheckState = toCheck.getState();
                    toCheckTaxRate = toCheck.getTaxRate();
                    toCheckProduct = toCheck.getProductType();
                    toCheckArea = toCheck.getArea();
                    toCheckUnitMatCost = toCheck.getMatCostPerSquareFoot();
                    toCheckUnitLaborCost = toCheck.getLaborCostPerSquareFoot();

                }
            }

            assertEquals("TEST FILE 2 NAME 3", toCheckName);
            assertEquals("IN", toCheckState);
            assertEquals(new BigDecimal("7.25"), toCheckTaxRate);
            assertEquals("TILE", toCheckProduct);
            assertEquals(new BigDecimal("200.00"), toCheckArea);
            assertEquals(new BigDecimal("5.15"), toCheckUnitMatCost);
            assertEquals(new BigDecimal("6.75"), toCheckUnitLaborCost);

        } catch (OrderDaoException ex) {
            fail("An exception is not expected to be thrown.");
        }
    }

    @Test
    public void testAddNewOrderNoFileCurrently() {

        try {
            //Initialize
            OrderDao toTest = new OrderDaoFileImpl("OrderTestFiles", "OrdersTest_");

            //Verify that file for 04/01/2020 does not exist
            boolean fileExists = Paths.get("OrderTestFiles", "OrdersTest_04012020.txt").toFile().exists();
            assertTrue(!fileExists);

            //Create new order for 04/01/2020
            Order testOrder = new Order();
            testOrder.setOrderDate(LocalDate.parse("04/01/2020", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            testOrder.setOrderNumber(1);
            testOrder.setCustomerName("TEST FILE 4 NAME 1");
            testOrder.setState("PA");
            testOrder.setTaxRate(new BigDecimal("8.25"));
            testOrder.setProductType("LAMINATE");
            testOrder.setArea(new BigDecimal("400.00"));
            testOrder.setMatCostPerSquareFoot(new BigDecimal("7.15"));
            testOrder.setLaborCostPerSquareFoot(new BigDecimal("8.75"));

            //Create new order file for 04/01/2020 and add 04/01/2020 order data
            toTest.addNewOrder(testOrder);

            //Get orders from new 04/01/2020 file
            List<Order> allOrderInfo = toTest.getOrders(LocalDate.of(2020,4,1));

            //Verify that the 04/01/2020 file has one order
            assertEquals(1, allOrderInfo.size());

            //Verify that the data in the new 04/01/2020 file is correct
            String toCheckOrderNumber = "Placeholder";
            String toCheckName = "Placeholder";
            String toCheckState = "Placeholder";
            BigDecimal toCheckTaxRate = BigDecimal.ZERO;
            String toCheckProduct = "Placeholder";
            BigDecimal toCheckArea = BigDecimal.ZERO;
            BigDecimal toCheckUnitMatCost = BigDecimal.ZERO;
            BigDecimal toCheckUnitLaborCost = BigDecimal.ZERO;

            for (Order toCheck : allOrderInfo) {
                if (toCheck.getOrderNumber() == 1) {
                    toCheckName = toCheck.getCustomerName();
                    toCheckState = toCheck.getState();
                    toCheckTaxRate = toCheck.getTaxRate();
                    toCheckProduct = toCheck.getProductType();
                    toCheckArea = toCheck.getArea();
                    toCheckUnitMatCost = toCheck.getMatCostPerSquareFoot();
                    toCheckUnitLaborCost = toCheck.getLaborCostPerSquareFoot();

                }
            }

            assertEquals("TEST FILE 4 NAME 1", toCheckName);
            assertEquals("PA", toCheckState);
            assertEquals(new BigDecimal("8.25"), toCheckTaxRate);
            assertEquals("LAMINATE", toCheckProduct);
            assertEquals(new BigDecimal("400.00"), toCheckArea);
            assertEquals(new BigDecimal("7.15"), toCheckUnitMatCost);
            assertEquals(new BigDecimal("8.75"), toCheckUnitLaborCost);

        } catch (OrderDaoException ex) {
            fail("An exception is not expected to be thrown.");
        }

    }
    
     @Test
    public void testDeleteOrderMultipleOrdersInFile() {
        
        try {
            //Initialize
            OrderDao toTest = new OrderDaoFileImpl("OrderTestFiles", "OrdersTest_");
            
            //Get orders from 02/01/2020 file
            List<Order> allOrderInfo = toTest.getOrders(LocalDate.of(2020,2,1));
            
            //Verify that the 02/01/2020 file has two orders in it
            assertEquals(2, allOrderInfo.size());
            
            //Delete the second order from the 02/01/2020 file
            toTest.delete(LocalDate.of(2020,2,1),2);
            
            //Get orders from updated 02/01/2020 file
            List<Order> allOrderInfoUpdated = toTest.getOrders(LocalDate.of(2020,2,1));
            
            //Verify that the 02/01/2020 file now has one order in it
            assertEquals(1, allOrderInfoUpdated.size());
            
            //Verify that the data remaining in the 02/01/2020 file is correct
            Order updatedOrder = new Order();
            for(Order toReturn : allOrderInfoUpdated){
                if(toReturn.getOrderNumber()==1){
                    updatedOrder = toReturn;    
                } 
            }
            
            assertEquals("TEST FILE 2 NAME 1", updatedOrder.getCustomerName());
            assertEquals("OH", updatedOrder.getState());
            assertEquals(new BigDecimal("6.25"), updatedOrder.getTaxRate());
            assertEquals("WOOD", updatedOrder.getProductType());
            assertEquals(new BigDecimal("100.00"), updatedOrder.getArea());
            assertEquals(new BigDecimal("5.15"), updatedOrder.getMatCostPerSquareFoot());
            assertEquals(new BigDecimal("4.75"), updatedOrder.getLaborCostPerSquareFoot());
            
            
        } catch (OrderDaoException ex) {
            fail("An exception is not expected to be thrown.");
        }
        
    }
        
    @Test
    public void testDeleteOrderOneOrderInFile() {
        
        try {
            //Initialize
            OrderDao toTest = new OrderDaoFileImpl("OrderTestFiles", "OrdersTest_");
            
            //Get orders from 03/01/2020 file
            List<Order> allOrderInfo = toTest.getOrders(LocalDate.of(2020,3,1));
            
            //Verify that the 03/01/2020 file has one order in it
            assertEquals(1, allOrderInfo.size());
            
            //Delete the order from the 03/01/2020 file
            toTest.delete(LocalDate.of(2020,3,1),1);
            
            //Verify that the 03/01/2020 file no longer exists
            boolean fileExists = Paths.get("OrderTestFiles", "OrdersTest_03012020.txt").toFile().exists();
            assertFalse(fileExists);
            
            
        } catch (OrderDaoException ex) {
            fail("An exception is not expected to be thrown.");
        }
        

    }
    
    @Test
    public void testEdit() {
        
        try {
            //Initialize
            OrderDao toTest = new OrderDaoFileImpl("OrderTestFiles", "OrdersTest_");
            
            //Get orders from 05/01/2020 file
            List<Order> allOrderInfo = toTest.getOrders(LocalDate.of(2020,5,1));
            
            //Verify the data in the 05/01/2020 file
            Order originalOrder = new Order();
            for(Order toReturn : allOrderInfo){
                if(toReturn.getOrderNumber()==1){
                    originalOrder = toReturn;    
                } 
            }
            
            assertEquals("TEST FILE 5 NAME 1", originalOrder.getCustomerName());
            assertEquals("OH", originalOrder.getState());
            assertEquals(new BigDecimal("6.25"), originalOrder.getTaxRate());
            assertEquals("WOOD", originalOrder.getProductType());
            assertEquals(new BigDecimal("100.00"), originalOrder.getArea());
            assertEquals(new BigDecimal("5.15"), originalOrder.getMatCostPerSquareFoot());
            assertEquals(new BigDecimal("4.75"), originalOrder.getLaborCostPerSquareFoot());
            
            
            //Create a new order with edited attributes
            Order editedOrder = new Order();
            editedOrder.setOrderDate(LocalDate.parse("05/01/2020", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            editedOrder.setOrderNumber(1);
            editedOrder.setCustomerName("EDITED NAME");
            editedOrder.setState("MN");
            editedOrder.setTaxRate(new BigDecimal("8.25"));
            editedOrder.setProductType("TEST PROD 1");
            editedOrder.setArea(new BigDecimal("200.00"));
            editedOrder.setMatCostPerSquareFoot(new BigDecimal("1.25"));
            editedOrder.setLaborCostPerSquareFoot(new BigDecimal("1.10"));
            
            //Edit order 1 in the 05/01/2020 file
            toTest.edit(editedOrder);
            
             //Get updated orders from 05/01/2020 file
            List<Order> allOrderInfoUpdated = toTest.getOrders(LocalDate.of(2020,5,1));
            
            //Verify the data in the 05/01/2020 file has been updated
            Order updatedOrder = new Order();
            for(Order toReturn : allOrderInfoUpdated){
                if(toReturn.getOrderNumber()==1){
                    updatedOrder = toReturn;    
                } 
            }
            
            assertEquals("EDITED NAME", updatedOrder.getCustomerName());
            assertEquals("MN", updatedOrder.getState());
            assertEquals(new BigDecimal("8.25"), updatedOrder.getTaxRate());
            assertEquals("TEST PROD 1", updatedOrder.getProductType());
            assertEquals(new BigDecimal("200.00"), updatedOrder.getArea());
            assertEquals(new BigDecimal("1.25"), updatedOrder.getMatCostPerSquareFoot());
            assertEquals(new BigDecimal("1.10"), updatedOrder.getLaborCostPerSquareFoot());
            
            
            
            
            
        } catch (OrderDaoException ex) {
            fail("An exception is not expected to be thrown.");
        }
        

    }

    
    
    
    
    
    

}
