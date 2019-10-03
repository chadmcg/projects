/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.dtos.Product;
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
public class ProductsDaoTest {

    String testFile = "Products_Testing.txt";
    String seedFile = "Products_Seed.txt";

    public ProductsDaoTest() {
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
     * Test of getProductInfo method, of class ProductsDao.
     */
    @Test
    public void testGetProductInfo() {

        try {
            //Initialize
            ProductsDao toTest = new ProductsDaoFileImpl("Products_Testing.txt");
            List<Product> allProductInfo = toTest.getProductInfo();

            //There are 6 products in the product test file
            //Verify that the correct number of records is returned
            assertEquals(6, allProductInfo.size());

            //Verify that the data returned for the first product is correct
            BigDecimal toCheckUnitMatCost = BigDecimal.ZERO;
            BigDecimal toCheckUnitLaborCost = BigDecimal.ZERO;
            for (Product toCheck : allProductInfo) {
                if (toCheck.getProductType().equals("Test Prod 1")) {
                    toCheckUnitMatCost = toCheck.getMatCostPerSquareFoot();
                    toCheckUnitLaborCost = toCheck.getLaborCostPerSquareFoot();
                }
            }

            assertEquals(new BigDecimal("1.25"), toCheckUnitMatCost);
            assertEquals(new BigDecimal("1.10"), toCheckUnitLaborCost);

            //Verify that the data returned for the last record is correct;
            for (Product toCheck : allProductInfo) {
                if (toCheck.getProductType().equals("Test Prod 6")) {
                    toCheckUnitMatCost = toCheck.getMatCostPerSquareFoot();
                    toCheckUnitLaborCost = toCheck.getLaborCostPerSquareFoot();
                }
            }

            assertEquals(new BigDecimal("6.25"), toCheckUnitMatCost);
            assertEquals(new BigDecimal("6.10"), toCheckUnitLaborCost);

        } catch (ProductsDaoException ex) {
            fail("An exception is not expected.");
        }

    }
    
    @Test
    public void testGetProductByNameValidProduct() {

        try {
            //Initialize
            ProductsDao toTest = new ProductsDaoFileImpl("Products_Testing.txt");
            
            //Product 1 is a valid product in the Products_Testing file
            Product productInfo = toTest.getProductInfoByName("Test Prod 1");
            
            //Verify that the product info returned is correct
            assertEquals(new BigDecimal("1.25"), productInfo.getMatCostPerSquareFoot());
            assertEquals(new BigDecimal("1.10"), productInfo.getLaborCostPerSquareFoot());
            
            //Product 6 is a valid product in the Products_Testing file
            productInfo = toTest.getProductInfoByName("Test Prod 6");
            
            //Verify that the product info returned is correct
            assertEquals(new BigDecimal("6.25"), productInfo.getMatCostPerSquareFoot());
            assertEquals(new BigDecimal("6.10"), productInfo.getLaborCostPerSquareFoot());
            

        } catch (ProductsDaoException ex) {
            fail("An exception is not expected.");
        }

    }
    
    @Test
    public void testGetProductByNameInvalidProduct() {

        try {
            //Initialize
            ProductsDao toTest = new ProductsDaoFileImpl("Products_Testing.txt");
            
            //Product 7 is not a valid product in the Products_Testing file
            Product productInfo = toTest.getProductInfoByName("Test Prod 7");
            
            //It is expected that the product object returned will not have a
            //material cost or a labor cost
            
            //Verify that the product object does not have a material cost
            assertEquals(null, productInfo.getMatCostPerSquareFoot());
            
            //Verify that the product object does not have a labor cost
            assertEquals(null, productInfo.getLaborCostPerSquareFoot());
          

        } catch (ProductsDaoException ex) {
            fail("An exception is not expected.");
        }

    }

    
    
    
    
    
    
    
    
    

}
