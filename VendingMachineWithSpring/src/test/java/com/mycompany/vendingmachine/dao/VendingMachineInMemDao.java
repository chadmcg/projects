/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;

import com.mycompany.vendingmachine.dao.VendingMachineDao;
import com.mycompany.vendingmachine.dao.VendingMachineDaoException;
import com.mycompany.vendingmachine.dto.Item;
import com.mycompany.vendingmachine.service.VendingMachineInsufficientFundsException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chad
 */
public class VendingMachineInMemDao implements VendingMachineDao{
    
    Item item1;
    Item item2;
    Item item3;
    
    
    List<Item> itemList = new ArrayList<>();
    
    public VendingMachineInMemDao(){
        item1 = new Item();
        item1.setItemID(1);
        item1.setItemName("Test Item 1");
        item1.setCurrentInventory(2);
        item1.setItemPrice(new BigDecimal("2.00"));
        
        itemList.add(item1);
        
        item2 = new Item();
        item2.setItemID(2);
        item2.setItemName("Test Item 2");
        item2.setCurrentInventory(0);
        item2.setItemPrice(new BigDecimal("2.00"));
        
        itemList.add(item2);
        
        item3 = new Item();
        item3.setItemID(3);
        item3.setItemName("Test Item 3");
        item3.setCurrentInventory(3);
        item3.setItemPrice(new BigDecimal("3.00"));
        
        itemList.add(item3);
        
        
    }

    @Override
    public List<Item> getInventory() throws VendingMachineDaoException {
        return itemList;
    }

    @Override
    public void updateInventory(int itemID) throws VendingMachineInsufficientFundsException, VendingMachineDaoException {
        List<Item> allItems = getInventory();

        for (Item currentItem : allItems) {
            if (currentItem.getItemID() == itemID) {
                currentItem.setCurrentInventory(currentItem.getCurrentInventory() - 1);
            }
        }

//        writeFile(allItems);
    }

    @Override
    public void writeFile(List<Item> allItems) throws VendingMachineInsufficientFundsException, VendingMachineDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Item> getNonzeroItems() throws VendingMachineDaoException {
        List<Item> currentInventory;

        currentInventory = getInventory();

        List<Item> nonzeroInventory = new ArrayList<>();

        for (int i = 0; i < currentInventory.size(); i++) {

            Item toCheck = currentInventory.get(i);

            if (toCheck.getCurrentInventory() > 0) {

                nonzeroInventory.add(toCheck);

            }

        }

        return nonzeroInventory;
    }
    }
    
    
    

