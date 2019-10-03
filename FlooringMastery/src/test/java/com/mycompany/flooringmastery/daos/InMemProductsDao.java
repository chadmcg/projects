/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.dtos.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chad
 */
public class InMemProductsDao implements ProductsDao{
    
    Product prod1;
    Product prod2;
    Product prod3;
    
    List<Product> productList = new ArrayList<>();
    
    public InMemProductsDao(){
        
        prod1 = new Product();
        prod1.setProductType("TEST-PROD1");
        prod1.setMatCostPerSquareFoot(new BigDecimal("3.50"));
        prod1.setLaborCostPerSquareFoot(new BigDecimal("4.50"));
        productList.add(prod1);
        
        prod2 = new Product();
        prod2.setProductType("TEST-PROD2");
        prod2.setMatCostPerSquareFoot(new BigDecimal("5.50"));
        prod2.setLaborCostPerSquareFoot(new BigDecimal("6.50"));
        productList.add(prod2);    
        
        prod3 = new Product();
        prod3.setProductType("TEST-PROD3");
        prod3.setMatCostPerSquareFoot(new BigDecimal("7.50"));
        prod3.setLaborCostPerSquareFoot(new BigDecimal("8.50"));
        productList.add(prod3);
        
    }

    @Override
    public List<Product> getProductInfo() throws ProductsDaoException {
        return productList;
        
    }

    @Override
    public Product getProductInfoByName(String custProd) throws ProductsDaoException {
        List<Product> allProducts = getProductInfo();
        Product toReturn = new Product();

        for (Product prod : allProducts) {

            if (prod.getProductType().equalsIgnoreCase(custProd)) {

                toReturn = prod;

            }

        }

        return toReturn;
    }
    }
        
        
    
    

