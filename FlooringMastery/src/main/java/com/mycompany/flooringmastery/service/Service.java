/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.service;

import com.mycompany.flooringmastery.daos.OrderDaoException;
import com.mycompany.flooringmastery.daos.ProductsDaoException;
import com.mycompany.flooringmastery.daos.TaxesDaoException;
import com.mycompany.flooringmastery.dtos.Order;
import com.mycompany.flooringmastery.dtos.Product;
import com.mycompany.flooringmastery.dtos.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Chad
 */
public interface Service {

    public List<Order> getOrdersByDate(LocalDate orderDate) throws OrderDaoException;

    public List<Order> getNonzeroOrdersByDate(LocalDate orderDate) throws OrderDaoException, OrderNotFoundForDateException;
    

    public List<Tax> getCurrentTaxInfo() throws TaxesDaoException;
    
    public List<Product> getCurrentProdInfo() throws ProductsDaoException;

    public boolean validateStateSelection(String custState) throws TaxesDaoException;

    public boolean validateProdSelection(String custProd) throws ProductsDaoException;
    
    public Order checkForOrderNum(int orderNumOfDelete, List<Order> OrdersToDisplay) throws OrderNumNotFoundException;
    

    public Order createNewOrder(String custName, String custState, String custProd, BigDecimal custArea, LocalDate orderDate) throws OrderDaoException, TaxesDaoException, ProductsDaoException,StateNotFoundException, ProductNotFoundException;

    public void editAnOrder(Order currentOrder, String editedName, String editedState, String editedProductType, BigDecimal editedArea) throws OrderDaoException, OrderNotFoundForDateException, ProductsDaoException, TaxesDaoException, StateNotFoundException, ProductNotFoundException;

    public void removeCustomerOrder(LocalDate orderDateOfDelete, int orderNumOfDelete) throws OrderDaoException;
    
}
