/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.controller;

import com.mycompany.vendingmachine.dao.VendingMachineDaoException;
import com.mycompany.vendingmachine.dto.Change;
import com.mycompany.vendingmachine.dto.Item;
import com.mycompany.vendingmachine.service.VendingMachineNoItemInventoryException;
import com.mycompany.vendingmachine.service.VendingMachineServiceLayer;
import com.mycompany.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.mycompany.vendingmachine.service.VendingMachineNoItemMatchException;
import com.mycompany.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author Chad
 */
public class VendingMachineController {

    VendingMachineView view;
    VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {

        this.view = view;
        this.service = service;

    }

    public void runProgram() {

        boolean continueProgram = true;
        while (continueProgram) {

            try {
                List<Item> nonzeroItems = service.getNonzeroItems();

                int userSelection = view.displayMenuGetUserSelection(nonzeroItems);

                switch (userSelection) {

                    case 1:
                        inputMoney();
                        break;
                    case 2:
                        selectAnItem();
                        break;
                    case 3:
                        returnChange();
                        break;
                    case 4:
                        continueProgram = false;
                        break;
                    default:
                        view.displayInvalidSelectionBanner();

                }
            } catch (VendingMachineDaoException ex) {
                view.displayErrorMessage(ex.getMessage());
            }

        }
        view.displayExitMessage();
    }

    private void inputMoney() {
        BigDecimal currentAmt = service.getCurrentMoneyInput();
        BigDecimal amtToAdd = view.getUserMoney().setScale(2, RoundingMode.HALF_UP);
        currentAmt = service.trackMoney(amtToAdd);

        view.moneyAddedSuccessMessage(amtToAdd, currentAmt);
    }

    private void selectAnItem() {

        try {
            service.moneyCheck();
            int userItemSelection = view.getUserItemSelection();
            Change userChange = service.selectItem(userItemSelection);
            view.displayUserChange(userChange);

        } catch (VendingMachineInsufficientFundsException | VendingMachineNoItemInventoryException | VendingMachineNoItemMatchException | VendingMachineDaoException ex) {
            view.displayErrorMessage(ex.getMessage());
        }

    }

    private void returnChange() {

        BigDecimal userChange = service.getCurrentMoneyInput();
        Change changeToReturn = new Change(userChange);
        service.resetMoney();
        view.displayUserChange(changeToReturn);

    }

}
