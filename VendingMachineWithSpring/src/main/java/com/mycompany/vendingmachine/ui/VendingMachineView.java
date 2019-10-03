/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.ui;

import com.mycompany.vendingmachine.dto.Change;
import com.mycompany.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Chad
 */
public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void displayNonzeroInventory(List<Item> nonzeroItems) {

        for (Item currentItem : nonzeroItems) {

            if (currentItem.getCurrentInventory() > 0) {
                io.print(currentItem.getItemID() + ": "
                        + currentItem.getItemName() + " "
                        + "(x" + currentItem.getCurrentInventory() + ") "
                        + "- $" + currentItem.getItemPrice());
            }

        }

    }

    public int displayMenuGetUserSelection(List<Item> nonzeroItems) {

        io.print("---------------------------------------");
        io.print("           CURRENT INVENTORY           ");
        io.print("---------------------------------------");

        displayNonzeroInventory(nonzeroItems);

        io.print("---------------------------------------");
        io.print("            VENDING OPTIONS            ");
        io.print("---------------------------------------");
        io.print("1. Insert money");
        io.print("2. Choose item");
        io.print("3. Return change");
        io.print("4. Exit");
        io.print("---------------------------------------");

        return io.readInt("Vending menu selection (1-4): ", 1, 4);

    }

    public int getUserItemSelection() {

        int itemID = io.readInt("Which item would you like? Please enter the ID (1-10) here: ", 1, 10);
        return itemID;

    }

    public void displayInStockMessage() {
        io.print("That item is in stock.");
    }

    public void displayOutOfStockMessage() {
        io.print("That item is out of stock. Please select a different item.");
    }

    public BigDecimal getUserMoney() {

        BigDecimal userMin = BigDecimal.ZERO;
        BigDecimal userMax = new BigDecimal("5");

        BigDecimal userDollarInput = io.readBigDecimal("How much would you like to enter? Please enter the amount here : ", userMin, userMax);
        return userDollarInput;

    }

    public void moneyAddedSuccessMessage(BigDecimal amtToAdd, BigDecimal currentAmt) {
        io.print("\n\n$" + amtToAdd + " sucessfully added. You have $" + currentAmt + " to spend.");
        io.print("You may continue to add money or choose an item to vend.");
    }

    public void displayErrorMessage(String errorMessage) {
        io.print(errorMessage);
    }

    public void displayUserChange(Change userChange) {
        io.print("Your change is: ");
        io.print("Dollars: " + Integer.toString(userChange.getDollars()));
        io.print("Quarters: " + Integer.toString(userChange.getQuarters()));
        io.print("Dimes: " + Integer.toString(userChange.getDimes()));
        io.print("Nickels: " + Integer.toString(userChange.getNickels()));
        io.print("Pennies: " + Integer.toString(userChange.getPennies()));
    }

    public void displayInvalidSelectionBanner() {
        io.print("An invalid selection was made.");
    }

    public void displayExitMessage() {
        io.print("Thank you! Goodbye!");
    }

}
