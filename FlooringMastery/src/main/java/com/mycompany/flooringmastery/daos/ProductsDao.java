/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.dtos.Product;
import java.util.List;

/**
 *
 * @author Chad
 */
public interface ProductsDao {
    
    List<Product> getProductInfo() throws ProductsDaoException;

    public Product getProductInfoByName(String custProd)throws ProductsDaoException;
    
    
}
