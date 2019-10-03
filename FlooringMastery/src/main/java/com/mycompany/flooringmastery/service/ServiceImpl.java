/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.service;

import com.mycompany.flooringmastery.daos.OrderDaoException;
import com.mycompany.flooringmastery.daos.OrderDaoFileImpl;
import com.mycompany.flooringmastery.daos.ProductsDaoException;
import com.mycompany.flooringmastery.daos.ProductsDaoFileImpl;
import com.mycompany.flooringmastery.daos.TaxesDaoException;
import com.mycompany.flooringmastery.daos.TaxesDaoFileImpl;
import com.mycompany.flooringmastery.dtos.Order;
import com.mycompany.flooringmastery.dtos.Product;
import com.mycompany.flooringmastery.dtos.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.mycompany.flooringmastery.daos.OrderDao;
import com.mycompany.flooringmastery.daos.TaxesDao;
import com.mycompany.flooringmastery.daos.ProductsDao;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chad
 */
public class ServiceImpl implements Service {

    OrderDao orderDao;
    TaxesDao taxesDao;
    ProductsDao productsDao;

    public ServiceImpl(OrderDao orderDao, TaxesDao taxesDao, ProductsDao productsDao) {
        this.orderDao = orderDao;
        this.taxesDao = taxesDao;
        this.productsDao = productsDao;

    }

    @Override
    public List<Tax> getCurrentTaxInfo() throws TaxesDaoException {
        List<Tax> currentTaxInfo = taxesDao.getTaxInfo();

        return currentTaxInfo;
    }

    @Override
    public List<Product> getCurrentProdInfo() throws ProductsDaoException {
        List<Product> currentProdInfo = productsDao.getProductInfo();

        return currentProdInfo;
    }

    @Override
    public boolean validateStateSelection(String custState) throws TaxesDaoException {

        boolean validSelection = false;
        List<Tax> currentTaxInfo = getCurrentTaxInfo();

        for (Tax taxEntry : currentTaxInfo) {

            if (taxEntry.getState().toLowerCase().equals(custState.toLowerCase())) {

                validSelection = true;
            }

        }

        return validSelection;

    }

    @Override
    public boolean validateProdSelection(String custProd) throws ProductsDaoException {

        boolean validSelection = false;
        List<Product> currentProdInfo = getCurrentProdInfo();

        for (Product productEntry : currentProdInfo) {

            if (productEntry.getProductType().toLowerCase().equals(custProd.toLowerCase())) {

                validSelection = true;
            }

        }

        return validSelection;

    }

    @Override
    public Order checkForOrderNum(int orderNumOfDelete, List<Order> ordersToDisplay) throws OrderNumNotFoundException {

        Order toReturn = null;

        for (Order orderToCheck : ordersToDisplay) {

            if (orderNumOfDelete == orderToCheck.getOrderNumber()) {

                toReturn = orderToCheck;

            }

        }

        if (toReturn == null) {
            throw new OrderNumNotFoundException("That order number does not exist for that order date.");
        }

        return toReturn;

    }

    @Override
    public List<Order> getOrdersByDate(LocalDate orderDate) throws OrderDaoException {

   

        List<Order> ordersForDate = orderDao.getOrders(orderDate);

        return ordersForDate;

    }

    @Override
    public List<Order> getNonzeroOrdersByDate(LocalDate orderDate) throws OrderDaoException, OrderNotFoundForDateException {

        String orderDateString = orderDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).replace("/", "");

        List<Order> ordersForDate = orderDao.getOrders(orderDate);

        if (ordersForDate.isEmpty()) {
            throw new OrderNotFoundForDateException("An order was not found for the date provided.");
        }

        return ordersForDate;
    }

    @Override
    public Order createNewOrder(String custName, String custState, String custProd, BigDecimal custArea, LocalDate orderDate) throws TaxesDaoException, ProductsDaoException, StateNotFoundException, ProductNotFoundException, OrderDaoException {

        boolean validProd = validateProdSelection(custProd);
        if(!validProd){
            throw new ProductNotFoundException ("The product provided is not valid.");
        }
        
        boolean validState = validateStateSelection(custState);
        if(!validState){
            throw new StateNotFoundException ("The state provided is not valid.");
        }
        
        
        Order newOrder = new Order();

        newOrder.setCustomerName(custName);

        newOrder.setState(custState);
        newOrder.setProductType(custProd);
        newOrder.setArea(custArea);
        
        Product prod = productsDao.getProductInfoByName(custProd);

        BigDecimal unitMatCost = prod.getMatCostPerSquareFoot();
        newOrder.setMatCostPerSquareFoot(unitMatCost);

        BigDecimal unitLaborCost = prod.getLaborCostPerSquareFoot();
        newOrder.setLaborCostPerSquareFoot(unitLaborCost);
        

        
        Tax orderTax = taxesDao.getTaxInfoByName(custState);

        BigDecimal taxRate = orderTax.getTaxRate();
        newOrder.setTaxRate(taxRate);

        newOrder.setOrderDate(orderDate);
        
        orderDao.addNewOrder(newOrder);

        return newOrder;

    }

    @Override
    public void editAnOrder(Order currentOrder, String editedName, String editedState, String editedProductType, BigDecimal editedArea) throws OrderDaoException, ProductsDaoException, TaxesDaoException, StateNotFoundException, ProductNotFoundException, OrderNotFoundForDateException {

        //Compare the attributes of the original order with the new attributes entered by the user.
        //Attributes--either updated ones or the original ones, if no change was elected--are added
        //to a new order object.
        
        boolean validProd = validateProdSelection(editedProductType);
        if(!validProd&& !editedProductType.equals("")){
            throw new ProductNotFoundException ("The product provided is not valid.");
        }
        
        boolean validState = validateStateSelection(editedState);
        if(!validState && !editedState.equals("")){
            throw new StateNotFoundException ("The state provided is not valid.");
        }
        
        List<Order> ordersByDay = getNonzeroOrdersByDate(currentOrder.getOrderDate());
        
        Order editedOrder = new Order();
        
        LocalDate editedOrderDate = currentOrder.getOrderDate();
        int editedOrderNum = currentOrder.getOrderNumber();

        if (editedName.equals("".trim())) {
            editedName = currentOrder.getCustomerName();
        }

        if (editedState.equals("".trim())) {
            editedState = currentOrder.getState();
        }

        if (editedProductType.equals("".trim())) {
            editedProductType = currentOrder.getProductType();
        }

        if (editedArea.equals(BigDecimal.ZERO)) {
            editedArea = currentOrder.getArea();
        }

        editedOrder.setOrderDate(editedOrderDate);
        editedOrder.setOrderNumber(editedOrderNum);
        editedOrder.setCustomerName(editedName);
        editedOrder.setState(editedState);
        editedOrder.setProductType(editedProductType);
        editedOrder.setArea(editedArea);

        Product prod = productsDao.getProductInfoByName(editedProductType);

        BigDecimal unitMatCost = prod.getMatCostPerSquareFoot();
        editedOrder.setMatCostPerSquareFoot(unitMatCost);
       
        
       BigDecimal unitLaborCost = prod.getLaborCostPerSquareFoot();
        editedOrder.setLaborCostPerSquareFoot(unitLaborCost);

       Tax orderTax = taxesDao.getTaxInfoByName(editedState);

        BigDecimal taxRate = orderTax.getTaxRate();
        editedOrder.setTaxRate(taxRate);
        
        this.orderDao.edit(editedOrder);

  
        
       
    }

    @Override
    public void removeCustomerOrder(LocalDate orderDateOfDelete, int orderNumOfDelete) throws OrderDaoException {

       orderDao.delete(orderDateOfDelete, orderNumOfDelete);
    }

}
