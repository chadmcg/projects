/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.service;

import com.mycompany.flooringmastery.daos.InMemNoOrdersDao;
import com.mycompany.flooringmastery.daos.InMemOrderDao;
import com.mycompany.flooringmastery.daos.InMemProductsDao;
import com.mycompany.flooringmastery.daos.InMemTaxesDao;
import com.mycompany.flooringmastery.daos.OrderDao;
import com.mycompany.flooringmastery.daos.OrderDaoException;
import com.mycompany.flooringmastery.daos.OrderDaoFileImpl;
import com.mycompany.flooringmastery.daos.ProductsDao;
import com.mycompany.flooringmastery.daos.ProductsDaoException;
import com.mycompany.flooringmastery.daos.TaxesDao;
import com.mycompany.flooringmastery.daos.TaxesDaoException;
import com.mycompany.flooringmastery.dtos.Order;
import com.mycompany.flooringmastery.dtos.Product;
import com.mycompany.flooringmastery.dtos.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class ServiceTest {

    private Service serv;

    public ServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getOrdersByDate method, of class Service.
     */
    @Test
    public void testValidateStateSelection() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();
            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "validateStateSelection"  method returns true if the state passed in
            //is present in the TaxesDao file and false if not
            //The three states in the InMemTaxesDao file are MN, WI and ND
            boolean validState1 = serv.validateStateSelection("MN");
            boolean validState2 = serv.validateStateSelection("WI");
            boolean validState3 = serv.validateStateSelection("ND");

            assertTrue(validState1);
            assertTrue(validState2);
            assertTrue(validState3);

            //CA, OR and WA are not in the InMemTaxesDao file
            boolean invalidState1 = serv.validateStateSelection("CA");
            boolean invalidState2 = serv.validateStateSelection("OR");
            boolean invalidState3 = serv.validateStateSelection("WA");

            assertFalse(invalidState1);
            assertFalse(invalidState2);
            assertFalse(invalidState3);

        } catch (TaxesDaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }

    @Test
    public void testValidateProdSelection() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();
            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "validateProductSelection"  method returns true if the product passed in
            //is present in the ProductsDao file and false if not
            //The three products in the InMemProductsDao file are TEST-PROD1, TEST-PROD2 AND TEST-PROD3
            boolean validProduct1 = serv.validateProdSelection("TEST-PROD1");
            boolean validProduct2 = serv.validateProdSelection("TEST-PROD2");
            boolean validProduct3 = serv.validateProdSelection("TEST-PROD3");

            assertTrue(validProduct1);
            assertTrue(validProduct2);
            assertTrue(validProduct3);

            //TEST-PROD4, TEST-PROD5 and TEST-PROD6 are not in the InMemProductsDao file
            boolean invalidProduct1 = serv.validateProdSelection("TEST-PROD4");
            boolean invalidProduct2 = serv.validateProdSelection("TEST-PROD5");
            boolean invalidProduct3 = serv.validateProdSelection("TEST-PROD6");

            assertFalse(invalidProduct1);
            assertFalse(invalidProduct2);
            assertFalse(invalidProduct3);

        } catch (ProductsDaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }

    @Test
    public void testGetCurrentTaxInfo() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "getCurrentTaxInfo" method returns info from the TaxesDao
            List<Tax> currentTaxInfo = serv.getCurrentTaxInfo();

            //The InMemTaxesDao has three records
            //Verify that the number of records returned is correct
            assertEquals(3, currentTaxInfo.size());

            //Verify that the data returned for the first InMemTaxesDao record is correct
            BigDecimal taxRateToCheck = BigDecimal.ZERO;
            for (Tax currentTaxRecord : currentTaxInfo) {
                if (currentTaxRecord.getState().equals("MN")) {
                    taxRateToCheck = currentTaxRecord.getTaxRate();
                }
            }

            assertEquals(new BigDecimal("8.25"), taxRateToCheck);

            //Verify that the data returned for the last InMemTaxesDao record is correct
            for (Tax currentTaxRecord : currentTaxInfo) {
                if (currentTaxRecord.getState().equals("ND")) {
                    taxRateToCheck = currentTaxRecord.getTaxRate();
                }
            }

            assertEquals(new BigDecimal("10.25"), taxRateToCheck);

        } catch (TaxesDaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }

    @Test
    public void testGetCurrentProdInfo() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "getCurrentProdInfo" method returns info from the ProductsDao
            List<Product> currentProdInfo = serv.getCurrentProdInfo();

            //The InMemProductsDao has three records
            //Verify that the number of records returned is correct
            assertEquals(3, currentProdInfo.size());

            //Verify that the data returned for the first InMemProductsDao record is correct
            BigDecimal matCostToCheck = BigDecimal.ZERO;
            BigDecimal laborCostToCheck = BigDecimal.ZERO;
            for (Product currentProductRecord : currentProdInfo) {
                if (currentProductRecord.getProductType().equals("TEST-PROD1")) {
                    matCostToCheck = currentProductRecord.getMatCostPerSquareFoot();
                    laborCostToCheck = currentProductRecord.getLaborCostPerSquareFoot();
                }
            }

            assertEquals(new BigDecimal("3.50"), matCostToCheck);
            assertEquals(new BigDecimal("4.50"), laborCostToCheck);

            //Verify that the data returned for the last InMemProductsDao record is correct
            for (Product currentProductRecord : currentProdInfo) {
                if (currentProductRecord.getProductType().equals("TEST-PROD3")) {
                    matCostToCheck = currentProductRecord.getMatCostPerSquareFoot();
                    laborCostToCheck = currentProductRecord.getLaborCostPerSquareFoot();
                }

            }

            assertEquals(new BigDecimal("7.50"), matCostToCheck);
            assertEquals(new BigDecimal("8.50"), laborCostToCheck);

        } catch (ProductsDaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }

   
   
    @Test
    public void testCheckForOrderNumValidOrderNum() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "checkForOrderNum" method cycles through the orders in a given list
            //If the provided order # is found, the relevant order is returned
            //If the provided order # is not found, an exception is thrown
            
            //A list of orders for date 1/1/2020 is returned from the InMemOrderDao
            List<Order> allOrders = serv.getOrdersByDate(LocalDate.of(2020,1,1));

            //There is an order with ID 1 for 1/1/20 in the InMemOrderDao
            Order validOrder = serv.checkForOrderNum(1, allOrders);

            //Verify that the order data returned is correct
            assertEquals("TEST CUST 1", validOrder.getCustomerName());
            assertEquals("MN", validOrder.getState());
            assertEquals(new BigDecimal("6.25"), validOrder.getTaxRate());
            assertEquals("TEST PROD 1", validOrder.getProductType());
            assertEquals(new BigDecimal("100.00"), validOrder.getArea());
            assertEquals(new BigDecimal("1.25"), validOrder.getMatCostPerSquareFoot());
            assertEquals(new BigDecimal("3.25"), validOrder.getLaborCostPerSquareFoot());

        } catch (OrderDaoException | OrderNumNotFoundException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }

    @Test
    public void testCheckForOrderNumInvalidOrderNum() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "checkForOrderNum" method cycles through the orders in a given list
            //If the provided order # is found, the relevant order is returned
            //If the provided order # is not found, an exception is thrown
            
            //A list of orders for 1/1/20 is returned from the InMemOrderDao
            List<Order> allOrders = serv.getOrdersByDate(LocalDate.of(2020,1,1));

            //There is not an order with ID 4 in the InMemOrderDao
            Order validOrder = serv.checkForOrderNum(4, allOrders);
            fail("It is expected that an OrderNumNotFoundException will be thrown.");

        } catch (OrderNumNotFoundException ex) {
            //It is expected that a OrderNumNotFoundException will be thrown.
        } catch (OrderDaoException ex) {
            fail("It is not expected that an exception other than OrderNumNotFoundExcepion will be thrown.");
        }

    }

    @Test
    public void testGetOrdersByDateOrdersPresent() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "getOrdersByDate" method returns a list of orders for a given date
            //If no orders are found for the provided date then an empty list is returned
            //This is a passthrough method; retrieval happens in the dao
            
            //Retrieve all orders for 1/1/2020
            List<Order> allOrders = serv.getOrdersByDate(LocalDate.of(2020,1,1));

            //Verify that the # of records returned is correct
            assertEquals(2, allOrders.size());

            //Verify that the data returned from the first record in the InMemDao is correct
            Order order1 = new Order();
            for (Order toCheck : allOrders) {
                if (toCheck.getOrderNumber() == 1) {
                    order1 = toCheck;
                }

            }

            assertEquals("TEST CUST 1", order1.getCustomerName());
            assertEquals("MN", order1.getState());
            assertEquals(new BigDecimal("6.25"), order1.getTaxRate());
            assertEquals("TEST PROD 1", order1.getProductType());
            assertEquals(new BigDecimal("100.00"), order1.getArea());
            assertEquals(new BigDecimal("1.25"), order1.getMatCostPerSquareFoot());
            assertEquals(new BigDecimal("3.25"), order1.getLaborCostPerSquareFoot());

            //Verify that the data returned from the last record in the InMemDao is correct
            Order order3 = new Order();
            for (Order toCheck : allOrders) {
                if (toCheck.getOrderNumber() == 3) {
                    order3 = toCheck;
                }

            }

            assertEquals("TEST CUST 3", order3.getCustomerName());
            assertEquals("OH", order3.getState());
            assertEquals(new BigDecimal("8.25"), order3.getTaxRate());
            assertEquals("TEST PROD 3", order3.getProductType());
            assertEquals(new BigDecimal("300.00"), order3.getArea());
            assertEquals(new BigDecimal("3.25"), order3.getMatCostPerSquareFoot());
            assertEquals(new BigDecimal("5.25"), order3.getLaborCostPerSquareFoot());

        } catch (OrderDaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }
    }

    @Test
    public void testGetOrdersByDateNoOrdersPresent() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "getOrdersByDate" method returns a list of orders for a given date
            //If no orders are found for the provided date then an empty list is returned
            //This is a passthrough method; retrieval happens in the dao
            
            //Retrieve all orders for 5/1/2020
            List<Order> allOrders = serv.getOrdersByDate(LocalDate.of(2020,5,1));

            //There are no orders for 5/1/2020. Verify that an empty list is returned
            assertEquals(0, allOrders.size());

        } catch (OrderDaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }

    @Test
    public void testGetNonzeroOrdersByDateOrdersPresent() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "getNonzeroOrdersByDate" method returns a list of orders for a given date
            //If no orders are found for the provided date then an exception is thrown
            //This is a passthrough method; retrieval happens in the dao
            
             //Retrieve all orders for 5/1/2020
            List<Order> allOrders = serv.getNonzeroOrdersByDate(LocalDate.of(2020,1,1));

            //Verify that the # of records returned is correct
            assertEquals(2, allOrders.size());

            //Verify that the data returned from the first record in the InMemDao is correct
            Order order1 = new Order();
            for (Order toCheck : allOrders) {
                if (toCheck.getOrderNumber() == 1) {
                    order1 = toCheck;
                }

            }

            assertEquals("TEST CUST 1", order1.getCustomerName());
            assertEquals("MN", order1.getState());
            assertEquals(new BigDecimal("6.25"), order1.getTaxRate());
            assertEquals("TEST PROD 1", order1.getProductType());
            assertEquals(new BigDecimal("100.00"), order1.getArea());
            assertEquals(new BigDecimal("1.25"), order1.getMatCostPerSquareFoot());
            assertEquals(new BigDecimal("3.25"), order1.getLaborCostPerSquareFoot());

            //Verify that the data returned from the last record in the InMemDao is correct
            Order order3 = new Order();
            for (Order toCheck : allOrders) {
                if (toCheck.getOrderNumber() == 3) {
                    order3 = toCheck;
                }

            }

            assertEquals("TEST CUST 3", order3.getCustomerName());
            assertEquals("OH", order3.getState());
            assertEquals(new BigDecimal("8.25"), order3.getTaxRate());
            assertEquals("TEST PROD 3", order3.getProductType());
            assertEquals(new BigDecimal("300.00"), order3.getArea());
            assertEquals(new BigDecimal("3.25"), order3.getMatCostPerSquareFoot());
            assertEquals(new BigDecimal("5.25"), order3.getLaborCostPerSquareFoot());

        } catch (OrderDaoException | OrderNotFoundForDateException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }

    @Test
    public void testGetNonzeroOrdersByDateNoOrdersPresent() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "getNonzeroOrdersByDate" method returns a list of orders for a given date
            //If no orders are found for the provided date then an exception is thrown
            //This is a passthrough method; retrieval happens in the dao
            
            //There are no orders for 8/1/2020
            List<Order> allOrders = serv.getNonzeroOrdersByDate(LocalDate.of(2020,8,1));
            fail("It is expected that an OrderNotFoundForOrderDateException will be thrown.");

        } catch (OrderNotFoundForDateException ex) {
            //It is expected that an OrderNotFoundForOrderDateException will be thrown
        } catch (OrderDaoException ex) {
            fail("It is not expected that an exception other than OrderNotFoundForOrderDateException will be thrown. ");
        }
    }

    @Test
    public void testCreateNewOrderGoldenPath() throws OrderDaoException {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "createNewOrder" method takes in customer attributes as parameters 
            //A new order is created from those attributes
            Order testOrder = serv.createNewOrder("Test Name", "MN", "TEST-PROD1", new BigDecimal("100.00"), LocalDate.parse("09/14/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));

            //Verify that the attributes of the order are correct
            //Unit mat cost, unit labor cost and tax rate are retrieved by submethods
            assertEquals("Test Name", testOrder.getCustomerName());
            assertEquals("MN", testOrder.getState());
            assertEquals("TEST-PROD1", testOrder.getProductType());
            assertEquals(new BigDecimal("100.00"), testOrder.getArea());
            assertEquals(new BigDecimal("3.50"), testOrder.getMatCostPerSquareFoot());
            assertEquals(new BigDecimal("4.50"), testOrder.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("8.25"), testOrder.getTaxRate());
        } catch (TaxesDaoException | ProductsDaoException | StateNotFoundException | ProductNotFoundException ex) {
            fail("It is not expected that an exception will be thrown");
        }

    }

    @Test
    public void testCreateNewOrderInvalidState() throws OrderDaoException {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "createNewOrder" method takes in customer attributes as parameters 
            //A new order is created from those attributes
            Order testOrder = serv.createNewOrder("Test Name", "CA", "TEST-PROD1", new BigDecimal("100.00"), LocalDate.parse("09/14/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));

            
        } catch (TaxesDaoException | ProductsDaoException  | ProductNotFoundException ex) {
            fail("It is not expected that an exception will be thrown");
        } catch (StateNotFoundException ex) {
            //It is expected that a StateNotFoundException will be thrown
        }

    }

    @Test
    public void testCreateNewOrderInvalidProduct() throws OrderDaoException {
        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "createNewOrder" method takes in customer attributes as parameters 
            //A new order is created from those attributes
            //"TEST-PROD4" is not a product in the InMemProductsDao. It is expected that a ProductNotFoundException will be thrown
            Order testOrder = serv.createNewOrder("Test Name", "MN", "TEST-PROD4", new BigDecimal("100.00"), LocalDate.parse("09/14/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            fail("It is expected that a StateNotFoundException will be thrown.");

        } catch (ProductNotFoundException ex) {
            //It is expected that a ProductNotFoundException will be thrown
        } catch (TaxesDaoException | ProductsDaoException | StateNotFoundException ex) {
            fail("It is not expected that an exception other than ProductNotFoundException will be thrown");
        }

    }

 

    @Test
    public void testEditAnOrderGoldenPath() {

        try {
            //Initialize
            OrderDao oDao = new OrderDaoFileImpl("OrderTestFiles", "OrdersTest_");
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "editAnOrder" method takes in an order number and new order attributes as parameters
            //The method compares the new order attributes to the current
            //An edited order is created with the new order attributes
            //If a blank (string attributes) or 0 (numeric attributes) the current attributes are not changed
            //The current order is deleted and is replaced by the edited order
            
            //A test order is created
            Order testOrder = new Order();
            testOrder.setOrderNumber(1);
            testOrder.setOrderDate(LocalDate.of(2020,1,1));
            testOrder.setCustomerName("Test Name");
            testOrder.setProductType("TEST-PROD3");
            testOrder.setState("MN");
            testOrder.setArea(new BigDecimal("100.00"));
            

            //The "editAnOrder" method is called with updated order attributes
            serv.editAnOrder(testOrder, "EDITED NAME", "WI", "TEST-PROD2", new BigDecimal("50.00"));
            
            //Verify that the updated order is accurately written to the file
            List<Order> updatedOrders = oDao.getOrders(LocalDate.of(2020,1,1));
            Order updatedOrder = new Order();
            for(Order toCheck : updatedOrders){
                
                if(toCheck.getOrderNumber()==1){
                    
                    updatedOrder = toCheck;
                    
                }
                
            }
            
            assertEquals("EDITED NAME", updatedOrder.getCustomerName());
            assertEquals("WI", updatedOrder.getState());
            assertEquals("TEST-PROD2", updatedOrder.getProductType());
            assertEquals(new BigDecimal("50.00"), updatedOrder.getArea());
            
            

        } catch (OrderDaoException | OrderNotFoundForDateException | ProductsDaoException | TaxesDaoException | StateNotFoundException | ProductNotFoundException ex) {
            fail("It is not expected that an exception will be thrown.");
        }
    }

    @Test
    public void testEditAnOrderInvalidState() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "editAnOrder" method takes in an order number and new order attributes as parameters
            //The method compares the new order attributes to the current
            //An edited order is created with the new order attributes
            //If a blank (string attributes) or 0 (numeric attributes) the current attributes are not changed
            //The current order is deleted and is replaced by the edited order
            //A test order is created
            Order testOrder = new Order();
            testOrder.setOrderNumber(1);
            testOrder.setCustomerName("Test Name");
            testOrder.setProductType("TEST-PROD3");
            testOrder.setState("MN");
            testOrder.setArea(new BigDecimal("100.00"));
            testOrder.setOrderDate(LocalDate.parse("09/14/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));

            //The "editAnOrder" method is called with updated order attributes
            //"CA" is not a valid state in the InMemDao
            serv.editAnOrder(testOrder, "EDITED NAME", "CA", "TEST-PROD2", new BigDecimal("50.00"));
            fail("It is expected that a StateNotFoundException will be thrown.");

        } catch (StateNotFoundException ex) {
            //It is expected that a StateNotFoundException will be thrown

        } catch (OrderDaoException | OrderNotFoundForDateException | ProductsDaoException | TaxesDaoException | ProductNotFoundException ex) {
            fail("It is not expected that an exception other than StateNotFoundException will be thrown.");
        }

    }

    @Test
    public void testEditAnOrderInvalidProduct() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "editAnOrder" method takes in an order number and new order attributes as parameters
            //The method compares the new order attributes to the current
            //An edited order is created with the new order attributes
            //If a blank (string attributes) or 0 (numeric attributes) the current attributes are not changed
            //The current order is deleted and is replaced by the edited order
            //A test order is created
            Order testOrder = new Order();
            testOrder.setOrderNumber(1);
            testOrder.setCustomerName("Test Name");
            testOrder.setProductType("TEST-PROD3");
            testOrder.setState("MN");
            testOrder.setArea(new BigDecimal("100.00"));
            testOrder.setOrderDate(LocalDate.parse("09/14/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));

            //The "editAnOrder" method is called with updated order attributes
            //"TEST-PROD4" is not a valid product in the InMemDao
            serv.editAnOrder(testOrder, "EDITED NAME", "MN", "TEST-PROD4", new BigDecimal("50.00"));
            fail("It is expected that a ProductsNotFoundException will be thrown.");

        } catch (ProductNotFoundException ex) {
            //It is expected that a ProductNotFoundException will be thrown

        } catch (OrderDaoException | OrderNotFoundForDateException | StateNotFoundException | TaxesDaoException | ProductsDaoException ex) {
            fail("It is not expected that an exception other than ProductsNotFoundException will be thrown.");
        }

    }

    @Test
    public void testEditAnOrderNoOrder() {

        try {
            //Initialize
            OrderDao oDao = new InMemOrderDao();
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "editAnOrder" method takes in an order number and new order attributes as parameters
            //The method compares the new order attributes to the current
            //An edited order is created with the new order attributes
            //If a blank (string attributes) or 0 (numeric attributes) the current attributes are not changed
            //The current order is deleted and is replaced by the edited order
            //A test order is created
            Order testOrder = new Order();
            testOrder.setOrderNumber(10);
            testOrder.setCustomerName("Test Name");
            testOrder.setProductType("TEST-PROD3");
            testOrder.setState("MN");
            testOrder.setArea(new BigDecimal("100.00"));
            testOrder.setOrderDate(LocalDate.of(2020,8,1));

            //The "editAnOrder" method is called with updated order attributes
            
            //There are not orders for 8/1/2020
            serv.editAnOrder(testOrder, "EDITED NAME", "MN", "TEST-PROD2", new BigDecimal("50.00"));
            fail("It is expected that an OrderNotFoundForDateException will be thrown.");

        } catch (OrderNotFoundForDateException ex) {
            //It is expected that an OrderNotFoundForDateException will be thrown

        } catch (OrderDaoException | ProductNotFoundException | StateNotFoundException | TaxesDaoException | ProductsDaoException ex) {
            fail("It is not expected that an exception other than OrderNotFoundForDateException will be thrown.");
        }

    }
    
    @Test
    public void testRemoveCustomerOrder() {

        try {
            //Initialize
            OrderDao oDao = new OrderDaoFileImpl("OrderTestFiles", "OrdersTest_");
            TaxesDao tDao = new InMemTaxesDao();
            ProductsDao pDao = new InMemProductsDao();

            serv = new ServiceImpl(oDao, tDao, pDao);

            //The "removeCustomerOrder" method takes in an order number and and an order date.
            //The corresponding order is deleted
            
            //Get a list of the current orders for 5/1/2020
            List<Order> currentOrders = serv.getOrdersByDate(LocalDate.of(2020,5,1));
            
            //Verify the size of the list of orders 
            assertEquals(2, currentOrders.size());
            
            //Remove the first order
            serv.removeCustomerOrder(LocalDate.of(2020,5,1), 1);
            
            //Get an updated list of orders for 5/1/2020
            List<Order> updatedOrders = serv.getOrdersByDate(LocalDate.of(2020,5,1));
            
            //Verify the size of the list of orders returned
            assertEquals(1, updatedOrders.size());
            
            //Verify that the data in the updated list of orders is correct
            String custName = "Placeholder";
            String state = "Placeholder";
            String prodType = "Placeholder";
            BigDecimal area = new BigDecimal("-1");
            
            for (Order orderToCheck : updatedOrders){
                if(orderToCheck.getOrderNumber()==2){
                    custName = orderToCheck.getCustomerName();
                    state = orderToCheck.getState();
                    prodType = orderToCheck.getProductType();
                    area = orderToCheck.getArea(); 
                }
            }
            
            assertEquals("TEST FILE 5 NAME 2", custName);
            assertEquals("MN", state);
            assertEquals("CARPET", prodType);
            assertEquals(new BigDecimal("300.00"), area);
            
            
            
        } catch (OrderDaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }
            

    }


}
