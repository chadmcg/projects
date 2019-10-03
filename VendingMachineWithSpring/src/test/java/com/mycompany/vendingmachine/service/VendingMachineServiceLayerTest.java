/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.service;

import com.mycompany.vendingmachine.dao.VendingMachineAlwaysFailsDao;
import com.mycompany.vendingmachine.dao.VendingMachineDao;
import com.mycompany.vendingmachine.dao.VendingMachineDaoException;
import com.mycompany.vendingmachine.dao.VendingMachineInMemDao;
import com.mycompany.vendingmachine.dto.Change;
import com.mycompany.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class VendingMachineServiceLayerTest {

    private VendingMachineServiceLayer service;

    public VendingMachineServiceLayerTest() {

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
     * Test of selectItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testSelectItemGoldenPath() {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        try {
            //Item 1 from InMemDao has inventory of 2 and a price of $2.00
            service.trackMoney(new BigDecimal("5.41"));
            Change userChange = service.selectItem(1);

            //Verify that item 1 has, after vending, an inventory of 1
            int currentInventory = -1;
            List<Item> allItems = dao.getInventory();
            for (Item itemToCheck : allItems) {

                if (itemToCheck.getItemID() == 1) {
                    currentInventory = itemToCheck.getCurrentInventory();
                }
            }

            assertEquals(1, currentInventory);

            //Verify that the change being returned is correct
            assertEquals(userChange.getDollars(), 3);
            assertEquals(userChange.getQuarters(), 1);
            assertEquals(userChange.getDimes(), 1);
            assertEquals(userChange.getNickels(), 1);
            assertEquals(userChange.getPennies(), 1);
        } catch (VendingMachineInsufficientFundsException | VendingMachineNoItemInventoryException | VendingMachineNoItemMatchException | VendingMachineDaoException ex) {
            fail("No exceptions are expected in the golden path case");
        }

    }

    @Test
    public void testSelectItemOutOfStock() {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        try {
            //Item 2 from InMemDao has inventory of 0 and a price of $2.00
            service.trackMoney(new BigDecimal("5.41"));
            Change userChange = service.selectItem(2);
            fail("It is expected that a VendingMachineNoItemInventoryException will be thrown.");

        } catch (VendingMachineInsufficientFundsException | VendingMachineNoItemMatchException | VendingMachineDaoException ex) {
            fail("It is not expected that an exception other than NoItemInventoryException will be thrown.");
        } catch (VendingMachineNoItemInventoryException ex) {

            try {
                //Verify that item 2 has, after vending, an inventory of 0
                int currentInventory = -1;
                List<Item> allItems = dao.getInventory();
                for (Item itemToCheck : allItems) {

                    if (itemToCheck.getItemID() == 2) {
                        currentInventory = itemToCheck.getCurrentInventory();
                    }
                }

                assertEquals(0, currentInventory);

                //Verify that the change returned after selecting an out of stock item is correct
                BigDecimal userChange = service.getCurrentMoneyInput();
                Change changeToReturn = new Change(userChange);
                assertEquals(changeToReturn.getDollars(), 5);
                assertEquals(changeToReturn.getQuarters(), 1);
                assertEquals(changeToReturn.getDimes(), 1);
                assertEquals(changeToReturn.getNickels(), 1);
                assertEquals(changeToReturn.getPennies(), 1);
            } catch (VendingMachineDaoException ex1) {
                fail("It is not expected that an exception other than NoItemInventoryException will be thrown.");
            }
        }
    }

    @Test
    public void testSelectInsufficientFunds() {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        try {

            //Item 1 from InMemDao has inventory of 2 and a price of $2.00
            service.trackMoney(new BigDecimal("0.01"));
            Change userChange = service.selectItem(1);

        } catch (VendingMachineNoItemInventoryException | VendingMachineNoItemMatchException | VendingMachineDaoException ex) {
            fail("It is not expected that an exception other than InsufficientFundsException will be thrown.");
        } catch (VendingMachineInsufficientFundsException ex) {

            try {
                //Verify that item 1 has, after vending, an inventory of 2
                int currentInventory = -1;
                List<Item> allItems = dao.getInventory();
                for (Item itemToCheck : allItems) {

                    if (itemToCheck.getItemID() == 1) {
                        currentInventory = itemToCheck.getCurrentInventory();
                    }
                }

                assertEquals(2, currentInventory);

                //Verify that the change being returned is correct
                BigDecimal userChange = service.getCurrentMoneyInput();
                Change changeToReturn = new Change(userChange);
                assertEquals(changeToReturn.getDollars(), 0);
                assertEquals(changeToReturn.getQuarters(), 0);
                assertEquals(changeToReturn.getDimes(), 0);
                assertEquals(changeToReturn.getNickels(), 0);
                assertEquals(changeToReturn.getPennies(), 1);
            } catch (VendingMachineDaoException ex1) {
                fail("It is not expected that an exception othan than InsufficientFundsException will be thrown");
            }

        }

    }

    @Test
    public void testSelectInsufficientFundsEdgeCase() {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        try {
            //Item 1 from InMemDao has inventory of 2 and a price of $2.00
            service.trackMoney(new BigDecimal("2.00"));
            Change userChange = service.selectItem(1);

            //Verify that item 1 has, after vending, an inventory of 1
            int currentInventory = -1;
            List<Item> allItems = dao.getInventory();
            for (Item itemToCheck : allItems) {

                if (itemToCheck.getItemID() == 1) {
                    currentInventory = itemToCheck.getCurrentInventory();
                }
            }

            assertEquals(1, currentInventory);

            //Verify that the change being returned is correct
            assertEquals(userChange.getDollars(), 0);
            assertEquals(userChange.getQuarters(), 0);
            assertEquals(userChange.getDimes(), 0);
            assertEquals(userChange.getNickels(), 0);
            assertEquals(userChange.getPennies(), 0);
        } catch (VendingMachineInsufficientFundsException | VendingMachineNoItemInventoryException | VendingMachineNoItemMatchException | VendingMachineDaoException ex) {
            fail("No exceptions are expected in the golden path case");
        }

    }

    @Test
    public void testSelectInvalidID() {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        try {

            //There is not an item with an ID of 12 set up in the InMemDao
            service.trackMoney(new BigDecimal("5.41"));
            Change userChange = service.selectItem(12);

        } catch (VendingMachineNoItemInventoryException | VendingMachineInsufficientFundsException | VendingMachineDaoException ex) {
            fail("It is not expected that an exception other than VendingMachineNoItemMatchException will be thrown.");
        } catch (VendingMachineNoItemMatchException ex) {

            try {
                //Verify that item 1 has, after vending, an inventory of 2. (No change is expected.)
                int currentInventory = -1;
                List<Item> allItems = dao.getInventory();
                for (Item itemToCheck : allItems) {

                    if (itemToCheck.getItemID() == 1) {
                        currentInventory = itemToCheck.getCurrentInventory();
                    }
                }

                assertEquals(2, currentInventory);

                //Verify that the change being returned is correct
                BigDecimal userChange = service.getCurrentMoneyInput();
                Change changeToReturn = new Change(userChange);
                assertEquals(changeToReturn.getDollars(), 5);
                assertEquals(changeToReturn.getQuarters(), 1);
                assertEquals(changeToReturn.getDimes(), 1);
                assertEquals(changeToReturn.getNickels(), 1);
                assertEquals(changeToReturn.getPennies(), 1);
            } catch (VendingMachineDaoException ex1) {
                fail("It is not expected that an exception othan than VendingMachineNoItemMatchException will be thrown");
            }
        }
    }

    @Test
    public void testMoneyCheckWithMoneyAdded() {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        try {
            //MoneyCheck throws an exception if current money input is not greater than $0.
            //This method does nothing other than throw an exception; nothing is returned and 
            //there is no output besides the exception

            //An input >$0.00 should not throw an InsufficientFundsException
            service.trackMoney(new BigDecimal("5.00"));
            service.moneyCheck();
        } catch (VendingMachineInsufficientFundsException ex) {
            fail("It is not expected that a VendingMachineInsufficientFundsException will be thrown");
        }
    }

    @Test
    public void testMoneyCheckWithNoMoneyAddded() {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        try {
            //MoneyCheck throws an exception if current money input is not greater than $0.
            //This method does nothing other than throw an exception; nothing is returned and 
            //there is no output besides the exception

            //An input of $0.00 should throw an InsufficientFundsException
            service.trackMoney(new BigDecimal("0.00"));
            service.moneyCheck();
        } catch (VendingMachineInsufficientFundsException ex) {
            //It is expected that an InsufficientFundsException will be thrown
            return;
        }

    }

    @Test
    public void testMoneyCheckWithNegativeMoney() throws Exception {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        try {
            //MoneyCheck throws an exception if current money input is not greater than $0.
            //This method does nothing other than throw an exception; nothing is returned and 
            //there is no output besides the exception

            //An input of <$0.00 should throw an InsufficientFundsException
            service.trackMoney(new BigDecimal("-2.00"));
            service.moneyCheck();
        } catch (VendingMachineInsufficientFundsException ex) {
            //It is expected that an InsufficientFundsException will be thrown
            return;
        }
    }

    @Test
    public void testMoneyCheckEdgeCase() {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        try {
            //MoneyCheck throws an exception if current money input is not greater than $0.
            //This method does nothing other than throw an exception; nothing is returned and 
            //there is no output besides the exception

            //An input >$0.00 should not throw an InsufficientFundsException
            service.trackMoney(new BigDecimal("0.01"));
            service.moneyCheck();
        } catch (VendingMachineInsufficientFundsException ex) {
            fail("It is not expected that a VendingMachineInsufficientFundsException will be thrown");
        }

    }

    /**
     * Test of getNonzeroItems method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetNonzeroItems() {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        try {

            //Passthrough method; retrieval happens in the dao
            //Of the Items in the VendingMachineInMemDao, 2 have nonzero inventory
            //Verify that the list returned has two members
            assertEquals(2, service.getNonzeroItems().size());

            //Verify that the item attributes returned are correct
            String itemToCheckName = "Not name";
            int itemToCheckInventory = 100;
            BigDecimal itemToCheckPrice = BigDecimal.TEN;

            List<Item> nonzeroItems = service.getNonzeroItems();
            for (Item itemToCheck : nonzeroItems) {

                if (itemToCheck.getItemID() == 3) {
                    itemToCheckName = itemToCheck.getItemName();
                    itemToCheckInventory = itemToCheck.getCurrentInventory();
                    itemToCheckPrice = itemToCheck.getItemPrice();
                }

            }

            assertEquals("Test Item 3", itemToCheckName);
            assertEquals(3, itemToCheckInventory);
            assertEquals(new BigDecimal("3.00"), itemToCheckPrice);
        } catch (VendingMachineDaoException ex) {
            fail("It is not expected that a VendingMachineDaoException will be thrown");
        }

    }

    @Test
    public void trackMoney() {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        //trackMoney totals all of the money added to the vending machine until change is dispensed
        //adding an initial amount
        service.trackMoney(new BigDecimal("5.41"));

        //adding an additional amount and returning the total tracked
        BigDecimal trackMoneyTotalTest = service.trackMoney(new BigDecimal("3.00"));

        assertEquals(new BigDecimal("8.41"), trackMoneyTotalTest);

    }

    @Test
    public void resetMoney() {

        //Initialize
        VendingMachineDao dao = new VendingMachineInMemDao();
        service = new VendingMachineServiceLayerImpl(dao);

        //resetMoney zeroes out the running total of money added to the vending machine
        //adding an initial amount
        service.trackMoney(new BigDecimal("5.41"));

        assertEquals(new BigDecimal("5.41"), service.getCurrentMoneyInput());

        //zeroing the amount input
        service.resetMoney();

        assertEquals(BigDecimal.ZERO, service.getCurrentMoneyInput());

    }

    @Test
    public void testSelectItemAlwaysFailsDao() {

        //Initialize
        VendingMachineDao dao = new VendingMachineAlwaysFailsDao();
        service = new VendingMachineServiceLayerImpl(dao);

        try {

            //Item 1 from InMemDao has inventory of 2 and a price of $2.00
            service.trackMoney(new BigDecimal("5.41"));
            Change userChange = service.selectItem(1);
            fail("It is expected that an exception will be thrown.");

        } catch (VendingMachineDaoException ex) {

            //Verify that the change being returned is correct
            BigDecimal userChange = service.getCurrentMoneyInput();
            Change changeToReturn = new Change(userChange);
            assertEquals(changeToReturn.getDollars(), 5);
            assertEquals(changeToReturn.getQuarters(), 1);
            assertEquals(changeToReturn.getDimes(), 1);
            assertEquals(changeToReturn.getNickels(), 1);
            assertEquals(changeToReturn.getPennies(), 1);

        } catch (VendingMachineInsufficientFundsException | VendingMachineNoItemInventoryException | VendingMachineNoItemMatchException ex) {
            fail("It is not expected that an exception other than VendingMachineDaoException would be thrown");
        }

    }

}
