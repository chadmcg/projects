/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.service;

import com.mycompany.vendingmachine.dao.VendingMachineDaoException;
import com.mycompany.vendingmachine.dto.Change;
import com.mycompany.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Chad
 */
public interface VendingMachineServiceLayer {

    public Change selectItem(int itemID) throws VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException,VendingMachineNoItemMatchException, VendingMachineDaoException;

    public BigDecimal getCurrentMoneyInput();

    public int getCurrentItemSelection();

    public void setCurrentItemSelection(int currentItemSelection);

    public void moneyCheck() throws VendingMachineInsufficientFundsException;
    
    public BigDecimal trackMoney (BigDecimal currentMoneyInput);

    public List<Item> getNonzeroItems() throws VendingMachineDaoException;

    public void resetMoney();

}
