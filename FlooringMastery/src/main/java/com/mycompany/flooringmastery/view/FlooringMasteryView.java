/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.view;

import com.mycompany.flooringmastery.dtos.Order;
import com.mycompany.flooringmastery.dtos.Product;
import com.mycompany.flooringmastery.dtos.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Chad
 */
public class FlooringMasteryView {

    private UserIO io;
    
    public FlooringMasteryView(UserIO io){
        
        this.io = io;
    }
   

    public int showMenuAndGetUserSelection() {

        io.print("\n-------------------");
        io.print("1. Display orders");
        io.print("2. Add an order");
        io.print("3. Edit an order");
        io.print("4. Remove an order");
        io.print("5. Quit");
        io.print("-------------------");

        int userSelection = io.readInt("Please note your menu selection here: ", 1, 5);

        return userSelection;
    }

    public void displayExitBanner() {
        io.print("\nThank you! Goodbye!");
    }

    public void displayInvalidSelectionBanner() {
        io.print("\nError: An invalid selection was made");
    }

    public LocalDate getOrderDateFromUser() {

        return io.readLocalDate("What day would you like to view orders for? Please enter the date (MM/DD/YYYY) here: ", LocalDate.parse("2009-12-31"),LocalDate.parse("2021-01-01"));

    }

    public void displayOrdersForDate(List<Order> ordersToDisplay) {

        io.print("\n--------------------------------------");
        io.print("ORDER NUM::CUST NAME::PROD TYPE::TOTAL");
        io.print("--------------------------------------");

        for (Order currentOrder : ordersToDisplay) {

            io.print(currentOrder.getOrderNumber() + "::"
                    + currentOrder.getCustomerName() + "::"
                    + currentOrder.getProductType() + "::$"
                    + currentOrder.getTotalCost());

        }

    }

    public void displayErrorMessageBanner(String message) {
        io.print("\n" + message);
    }

    public void displayCreateNewOrderBanner() {

        io.print("\n--------------------------------------");
        io.print("          CREATE A NEW ORDER          ");
        io.print("--------------------------------------");

    }

    public String getNewOrderName() {

        String custName = io.readString("What is the customer's name? ");
        return custName;

    }

    public String getNewOrderState() {
        String stateName = io.readString("What state is the customer in? ");
        return stateName;
    }

    public String getNewOrderProd() {

        String prodName = io.readString("What product is the customer purchasing? ");
        return prodName;

    }

    public BigDecimal getNewOrderArea() {
        BigDecimal prodArea = io.readBigDecimal("How many square feet of product is the customer purchasing? ", BigDecimal.ZERO,new BigDecimal("15000"));
        return prodArea;
    }

    public LocalDate getNewOrderInstallDate() {
        LocalDate orderDate = io.readLocalDate("On what date (MM/DD/YYYY) is the product going to be installed? ", LocalDate.now(),LocalDate.parse("2021-01-01"));
        return orderDate;
    }

    public void displayValidStates(List<Tax> currentTaxInfo) {

        io.print("The states that we can do business in are: ");

        for (Tax availableState : currentTaxInfo) {

            io.print("   " + availableState.getState());
        }
        
        io.print("");
        
    }

    public void displayValidProducts(List<Product> currentProdInfo) {
        
        io.print("The products available for purchase are: ");

        for (Product availableProduct : currentProdInfo) {

            io.print("   " + availableProduct.getProductType());

        }
        io.print("");
    }

    public void displayNewOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void displayEnteredOrderInfo(String custName, String custState, String custProd, BigDecimal custArea, LocalDate orderDate) {
        
        io.print("Here is the order information that was entered: \n");
        io.print("    Name: " + custName);
        io.print("    State: " + custState);
        io.print("    Product: " + custProd);
        io.print("    Area: " + custArea);
        io.print("    Order date: " + orderDate);
        
    }

    public String getAddOrderCommitResponse() {
        
        return io.readString("\nWould you like to continue? Please press \"Y\" to continue or any other key to discard the order. ");
        
    }

    public void displayOrderAddedBanner() {
        io.print("\nOrder has been successfully added.");
    }

    public void displayOrderDiscardedBanner() {
        io.print("\nOrder changes have been discarded.");
    }

    public void displayNoOrdersForDateBanner() {
        io.print("\nThere are no orders for that date.");
    }

    public LocalDate getOrderDateOfOrderToDelete() {
        return io.readLocalDate("What is the order date of the order you would like to remove? Please enter the date (MM/DD/YYYY) here: ");
    }

    public int getOrderNumberOfOrderToDelete() {
        return io.readInt("What is the order number of the order you would like to remove? Please enter the order number here: ");
    }

    public void displayNoOrderNumForOrderDateBanner() {
        io.print("\nThat order number does not exist for that order date.");
    }

    public void displayInfoToBeDeleted(Order orderToReturn) {
    
        io.print("Here is a summary of the order that will be deleted: \n");
        io.print("    Name: " + orderToReturn.getCustomerName());
        io.print("    State: " + orderToReturn.getState());
        io.print("    Product: " + orderToReturn.getProductType());
        io.print("    Area: " + orderToReturn.getArea());
        io.print("    Total cost: " + orderToReturn.getTotalCost());
        
    }

    public String getDeleteOrderCommitResponse() {
        return io.readString("\nWould you like to continue with the delete? Please press \"Y\" to continue or any other key to abandon the delete. ");
    }

    public void displayOrderDeletedBanner() {
        io.print("\nOrder has been successfully deleted.");
    }

    public String displayCurrentNameGetUpdate(String currentName) {
        
        io.print("\nName: " + currentName);
        return io.readString("Would you like to edit? If no, press enter; otherwise, please enter the updated name here: ");
        
    }

    public String displayCurrentStateGetUpdate(String currentState) {
        
        io.print("\nState: " + currentState);
        return io.readString("Would you like to edit? If no, press enter; otherwise, please enter the updated state here: ");
        
    }

    public String displayCurrentProductGetUpdate(String currentProductType) {
        
        io.print("\nProduct: " + currentProductType);
        return io.readString("Would you like to edit? If no, press enter; otherwise, please enter the updated name here: ");
        
    }

    public BigDecimal displayCurrentAreaGetUpdate(BigDecimal currentArea) {
        
        io.print("\nArea: " + currentArea);
        return io.readBigDecimal("Would you like to edit? If no, press zero; otherwise, please enter the updated area here: ");
        
    }

    public void OrderDeleteAbandonedBanner() {
        io.print("\nOrder delete has been abandoned.");
    }

    public LocalDate getOrderDateOfOrderToEdit() {
        return io.readLocalDate("What is the order date of the order you would like to edit? Please enter the date (MM/DD/YYYY) here: ");
    }

    public int getOrderNumberOfOrderToEdit() {
        return io.readInt("What is the order number of the order you would like to edit? Please enter the order number here: ");
    }

    public void displayOrderUpdatedBanner() {
        io.print("\nOrder has been updated.");
    }

    public void displayEditOrderBanner() {
        io.print("\n--------------------------------------");
        io.print("              EDIT ORDER              ");
        io.print("--------------------------------------");
    }

    public void displayRemoveOrderBanner() {
        io.print("\n--------------------------------------");
        io.print("             REMOVE ORDER             ");
        io.print("--------------------------------------");
    }

}
