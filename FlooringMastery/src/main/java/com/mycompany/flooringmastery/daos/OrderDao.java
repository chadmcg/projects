/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.dtos.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Chad
 */
public interface OrderDao {
    
    List<Order> getOrders(LocalDate date) throws OrderDaoException;

    public void addNewOrder(Order newOrder) throws OrderDaoException;

    public void delete(LocalDate orderDate, int orderNum) throws OrderDaoException;
    
    public void edit( Order editedOrder ) throws OrderDaoException;
    
    
    
}
