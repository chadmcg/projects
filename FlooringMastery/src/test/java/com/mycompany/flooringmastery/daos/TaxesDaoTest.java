/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.dtos.Tax;
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
public class TaxesDaoTest {

    String testFile = "Taxes_Testing.txt";
    String seedFile = "Taxes_Seed.txt";

    public TaxesDaoTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

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
     * Test of getTaxInfo method, of class TaxesDao.
     */
    @Test
    public void testGetTaxInfo() {

        try {
            //Initialize
            TaxesDao toTest = new TaxesDaoFileImpl("Taxes_Testing.txt");
            List<Tax> allTaxInfo = toTest.getTaxInfo();

            //There are 5 items in the test file
            //Verify that the correct number of records is returned
            assertEquals(5, allTaxInfo.size());

            //Verify that the data returned for the first record is correct
            BigDecimal toCheckTaxRate = BigDecimal.ZERO;
            for (Tax toCheck : allTaxInfo) {
                if (toCheck.getState().equals("OH")) {
                    toCheckTaxRate = toCheck.getTaxRate();
                }
            }

            assertEquals(new BigDecimal("7.25"), toCheckTaxRate);

            //Verify that the data returned for the last record is correct;
            for (Tax toCheck : allTaxInfo) {
                if (toCheck.getState().equals("MN")) {
                    toCheckTaxRate = toCheck.getTaxRate();
                }
            }

            assertEquals(new BigDecimal("8.50"), toCheckTaxRate);

        } catch (TaxesDaoException ex) {
            fail("An exception is not expected.");
        }

    }

    @Test
    public void testGetTaxInfoByNameValidState() {

        try {
            //Initialize
            TaxesDao toTest = new TaxesDaoFileImpl("Taxes_Testing.txt");

            //OH is a valid state in the Taxes_Testing file
            Tax taxInfo = toTest.getTaxInfoByName("OH");

            //Verify that the tax info returned is correct
            assertEquals(new BigDecimal("7.25"), taxInfo.getTaxRate());

            //MN is a valid state in the Taxes_Testing file
            Tax taxInfo2 = toTest.getTaxInfoByName("MN");

            //Verify that the tax info returned is correct
            assertEquals(new BigDecimal("8.50"), taxInfo2.getTaxRate());

        } catch (TaxesDaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }

    @Test
    public void testGetTaxInfoByNameInvalidState() {

        try {
            //Initialize
            TaxesDao toTest = new TaxesDaoFileImpl("Taxes_Testing.txt");

            //CA is not a valid state in the Taxes_Testing file
            Tax taxInfo = toTest.getTaxInfoByName("CA");

            //It is expected that the tax object returned will
            //not have a state or tax rate 
            
            //Verify that the tax object does not have a state 
            assertEquals(null, taxInfo.getState());

            //Verify that the tax object does not have a tax rate 
            assertEquals(null, taxInfo.getTaxRate());

        } catch (TaxesDaoException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }

}
