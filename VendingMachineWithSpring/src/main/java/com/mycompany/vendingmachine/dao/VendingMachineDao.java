/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;

import com.mycompany.vendingmachine.dto.Item;
import com.mycompany.vendingmachine.service.VendingMachineInsufficientFundsException;
import java.util.List;

/**
 *
 * @author Chad
 */
public interface VendingMachineDao {

    List<Item> getInventory() throws VendingMachineDaoException;
    
    public List<Item> getNonzeroItems() throws VendingMachineDaoException;

    public void updateInventory(int itemID) throws VendingMachineInsufficientFundsException, VendingMachineDaoException;

    public void writeFile(List<Item> allItems) throws VendingMachineInsufficientFundsException, VendingMachineDaoException;

}
