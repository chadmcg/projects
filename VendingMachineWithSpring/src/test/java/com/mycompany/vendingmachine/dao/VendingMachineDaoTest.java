/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;

import com.mycompany.vendingmachine.dto.Item;
import com.mycompany.vendingmachine.service.VendingMachineInsufficientFundsException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
public class VendingMachineDaoTest {

    String testFile = "Test.txt";
    String seedFile = "Seed.txt";

    public VendingMachineDaoTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp()  {
        
        Path testPath = Path.of(testFile);
        Path seedPath = Path.of(seedFile);

        try {
            if (Files.exists(testPath, LinkOption.NOFOLLOW_LINKS)) {
                Files.delete(testPath);
            }

            Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            fail("There was an error with the file set-up.");
        }

    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getInventory method, of class VendingMachineDao.
     */
    @Test
    public void testGetInventoryGoldenPath() {

        try {

            //Initialize
            VendingMachineDao toTest = new VendingMachineDaoFileImpl(testFile);
            List<Item> allItems = toTest.getInventory();

            //There are three items in the test file
            //Verify that the correct number of records is returned
            assertEquals(3, allItems.size());

            //Verify that the data in the records returned is correct
            String itemToCheckName = "Placeholder";
            int itemToCheckInventory = -1;
            BigDecimal itemToCheckPrice = new BigDecimal("10.00");

            for (Item itemToCheck : allItems) {

                if (itemToCheck.getItemID() == 1) {

                    itemToCheckName = itemToCheck.getItemName();
                    itemToCheckInventory = itemToCheck.getCurrentInventory();
                    itemToCheckPrice = itemToCheck.getItemPrice();
                }

                assertEquals("Test 1", itemToCheckName);
                assertEquals(0, itemToCheckInventory);
                assertEquals(new BigDecimal("1.25"), itemToCheckPrice);
            }
        } catch (VendingMachineDaoException ex) {
            fail("A VendingMachineDaoException is not expected.");
        }
    }

    /**
     * Test of getNonzeroItems method, of class VendingMachineDao.
     */
    @Test
    public void testGetNonzeroItemsGoldenPath() {
        try {

            //Initialize
            VendingMachineDao toTest = new VendingMachineDaoFileImpl(testFile);
            List<Item> allItems = toTest.getNonzeroItems();

            //There are two item in the test file that have non-zero inventory
            //Verify that the correct number of records is returned
            assertEquals(2, allItems.size());

            //Verify that the data in the records returned is correct
            String itemToCheckName = "Placeholder";
            int itemToCheckInventory = -1;
            BigDecimal itemToCheckPrice = new BigDecimal("10.00");

            for (Item itemToCheck : allItems) {

                if (itemToCheck.getItemID() == 2) {

                    itemToCheckName = itemToCheck.getItemName();
                    itemToCheckInventory = itemToCheck.getCurrentInventory();
                    itemToCheckPrice = itemToCheck.getItemPrice();
                }

                assertEquals("Test 2", itemToCheckName);
                assertEquals(2, itemToCheckInventory);
                assertEquals(new BigDecimal("0.75"), itemToCheckPrice);
            }
        } catch (VendingMachineDaoException ex) {
            fail("A VendingMachineDaoException is not expected.");
        }

    }

    
    /**
     * Test of updateInventory method, of class VendingMachineDao.
     */
    @Test
    public void testUpdateInventoryAndWriteFile() {
        
        try {

            //Initialize
            VendingMachineDao toTest = new VendingMachineDaoFileImpl(testFile);
            List<Item> allItemsInitial = toTest.getInventory();
            
            //Verify that the starting inventory of item 3 is 3
            int itemToCheckInventory = -1;

            for (Item itemToCheck : allItemsInitial) {

                if (itemToCheck.getItemID() == 3) {
                    itemToCheckInventory = itemToCheck.getCurrentInventory();
                }
            }
                assertEquals(3, itemToCheckInventory);
            
            try {
                //Update the inventory (i.e. decrease by 1) of item 3.
                toTest.updateInventory(3);
            } catch (VendingMachineInsufficientFundsException ex) {
                fail("It is not expected that a VendingMachineInsufficientFundsExceptino will be thrown.");
            }
            
            //Verify that the new inventory of item 3 is 2
            List<Item> allItemsUpdated = toTest.getInventory();
            
            itemToCheckInventory = -1;

            for (Item itemToCheck : allItemsUpdated) {

                if (itemToCheck.getItemID() == 3) {
                    itemToCheckInventory = itemToCheck.getCurrentInventory();
                }
            }
                assertEquals(2, itemToCheckInventory);

            
        } catch (VendingMachineDaoException ex) {
            fail("A VendingMachineDaoException is not expected.");
        }

        
        
    }
}
