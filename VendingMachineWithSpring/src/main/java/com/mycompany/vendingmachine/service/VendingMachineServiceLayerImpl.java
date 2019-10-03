/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.service;

import com.mycompany.vendingmachine.dao.VendingMachineDao;
import com.mycompany.vendingmachine.dao.VendingMachineDaoException;
import com.mycompany.vendingmachine.dto.Change;
import com.mycompany.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chad
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    VendingMachineDao dao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao) {
        this.dao = dao;

    }

    BigDecimal currentMoneyInput = BigDecimal.ZERO;
    int currentItemSelection = -1;

    @Override
    public Change selectItem(int itemID) throws VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException, VendingMachineNoItemMatchException, VendingMachineDaoException {

        //Loop through all items. If a match is found, return the item's current
        //inventory and the price of the item.
        List<Item> itemInventory = dao.getInventory();

        int currentInventory = -1;
        BigDecimal itemPrice = new BigDecimal("100");
        
        boolean itemIDmatch = false;
        for (Item currentItem : itemInventory) {
            if (currentItem.getItemID() == itemID) {
                currentInventory = currentItem.getCurrentInventory();
                itemPrice = currentItem.getItemPrice();
                itemIDmatch = true;
            } 
        }
        
        //If the id provided by the user does not match any items in inventory throw exception
        if(itemIDmatch == false){
            
            throw new VendingMachineNoItemMatchException("The item ID that was input is not valid. Please choose again.");
            
        }

        //If there is not enough inventory to fulfill the selection throw exception
        if (currentInventory < 1) {

            throw new VendingMachineNoItemInventoryException("That item is sold out. Please choose a different item.");
        }

        //check to see if the user has enough money to pay for the item selected.
        //If user does not have enough money to pay for the item selected then
        //throw exception
        
                
        if (currentMoneyInput.compareTo(itemPrice) < 0) {

            throw new VendingMachineInsufficientFundsException("The price of that item is " + itemPrice + ". $"
                    + currentMoneyInput + " has been entered. \nPlease add more money or make another selection.\n");
        }

        //Update the inventory file. (Inventory for the selected item should be
        //reduced by 1.
        dao.updateInventory(itemID);

        //Return change to the user. Reset money input to 0.
        BigDecimal changeToReturn = currentMoneyInput.subtract(itemPrice);
        Change userChange = new Change(changeToReturn);
        currentMoneyInput = BigDecimal.ZERO;

        return userChange;

    }

    @Override
    public void moneyCheck() throws VendingMachineInsufficientFundsException {

        if (currentMoneyInput.compareTo(new BigDecimal("0")) <= 0) {

            throw new VendingMachineInsufficientFundsException("Please enter money before making a selection.");

        }
    }

    @Override
    public BigDecimal getCurrentMoneyInput() {
        return currentMoneyInput;
    }


    @Override
    public int getCurrentItemSelection() {
        return currentItemSelection;
    }

    @Override
    public void setCurrentItemSelection(int currentItemSelection) {
        this.currentItemSelection = currentItemSelection;
    }

    @Override
    public List<Item> getNonzeroItems() throws VendingMachineDaoException {

       return dao.getNonzeroItems();
    }

    @Override
    public BigDecimal trackMoney(BigDecimal amtToAdd) {
        
        currentMoneyInput = currentMoneyInput.add(amtToAdd);
        
        return currentMoneyInput;
        
    }

    @Override
    public void resetMoney() {
        
        currentMoneyInput = BigDecimal.ZERO;
        
    }

}
