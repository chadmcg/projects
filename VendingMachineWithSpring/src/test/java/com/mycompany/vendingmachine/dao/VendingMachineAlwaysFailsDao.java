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
public class VendingMachineAlwaysFailsDao implements VendingMachineDao {

    @Override
    public List<Item> getInventory() throws VendingMachineDaoException {
        throw new VendingMachineDaoException("ALWAYS FAILS DAO"); 
    }

    @Override
    public List<Item> getNonzeroItems() throws VendingMachineDaoException {
        throw new VendingMachineDaoException("ALWAYS FAILS DAO"); 
    }

    @Override
    public void updateInventory(int itemID) throws VendingMachineInsufficientFundsException, VendingMachineDaoException {
        throw new VendingMachineDaoException("ALWAYS FAILS DAO"); 
    }

    @Override
    public void writeFile(List<Item> allItems) throws VendingMachineInsufficientFundsException, VendingMachineDaoException {
        throw new VendingMachineDaoException("ALWAYS FAILS DAO"); 
    }
    
}
