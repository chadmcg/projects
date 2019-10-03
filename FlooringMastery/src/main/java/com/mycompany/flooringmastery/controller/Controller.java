/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.controller;

import com.mycompany.flooringmastery.daos.OrderDaoException;
import com.mycompany.flooringmastery.daos.ProductsDaoException;
import com.mycompany.flooringmastery.daos.TaxesDaoException;
import com.mycompany.flooringmastery.dtos.Order;
import com.mycompany.flooringmastery.dtos.Product;
import com.mycompany.flooringmastery.dtos.Tax;
import com.mycompany.flooringmastery.service.OrderNotFoundForDateException;
import com.mycompany.flooringmastery.service.OrderNumNotFoundException;
import com.mycompany.flooringmastery.service.ProductNotFoundException;
import com.mycompany.flooringmastery.service.Service;
import com.mycompany.flooringmastery.service.ServiceImpl;
import com.mycompany.flooringmastery.service.StateNotFoundException;
import com.mycompany.flooringmastery.view.FlooringMasteryView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chad
 */
public class Controller {

    FlooringMasteryView view;
    Service service;

    public Controller(FlooringMasteryView view, Service service) {

        this.view = view;
        this.service = service;
    }

    public void runProgram() {

        boolean continueProgram = true;

        while (continueProgram) {

            int userSelection = view.showMenuAndGetUserSelection();

            switch (userSelection) {

                case 1:
                    displayOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    continueProgram = false;
                    break;
                default:
                    view.displayInvalidSelectionBanner();
            }

        }

        view.displayExitBanner();
    }

    private void displayOrders() {
        try {
            LocalDate orderDate = view.getOrderDateFromUser();
            List<Order> ordersToDisplay = service.getOrdersByDate(orderDate);
            if (ordersToDisplay.size() > 0) {
                view.displayOrdersForDate(ordersToDisplay);
            } else {
                view.displayNoOrdersForDateBanner();
            }
        } catch (OrderDaoException ex) {
            view.displayErrorMessageBanner(ex.getMessage());
        }
    }

    private void addOrder() {
        try {
            view.displayCreateNewOrderBanner();
            String custName = view.getNewOrderName();

            List<Tax> currentTaxInfo = service.getCurrentTaxInfo();
            view.displayValidStates(currentTaxInfo);
            String custState = "Invalid State";
            boolean validState = false;
            while (!validState) {
                custState = view.getNewOrderState();
                validState = service.validateStateSelection(custState);
            }

            List<Product> currentProdInfo = service.getCurrentProdInfo();
            view.displayValidProducts(currentProdInfo);
            String custProd = "Invalid Product";
            boolean validProduct = false;
            while (!validProduct) {
                custProd = view.getNewOrderProd();
                validProduct = service.validateProdSelection(custProd);
            }

            BigDecimal custArea = view.getNewOrderArea();
            LocalDate orderDate = view.getNewOrderInstallDate();

            view.displayEnteredOrderInfo(custName, custState, custProd, custArea, orderDate);

            String commitToOrder = view.getAddOrderCommitResponse();

            if (commitToOrder.toLowerCase().equals("y")) {

                Order newOrder = service.createNewOrder(custName, custState, custProd, custArea, orderDate);
                view.displayOrderAddedBanner();

            } else {

                view.displayOrderDiscardedBanner();
            }

        } catch (StateNotFoundException | TaxesDaoException | ProductsDaoException | OrderDaoException | ProductNotFoundException ex) {
            view.displayErrorMessageBanner(ex.getMessage());
        }

    }

    private void editOrder() {

        try {
            view.displayEditOrderBanner();
            LocalDate orderDateOfEdit = view.getOrderDateOfOrderToEdit();
            List<Order> ordersToDisplay = service.getNonzeroOrdersByDate(orderDateOfEdit);

            int orderNumOfEdit = view.getOrderNumberOfOrderToEdit();
            Order currentOrder = service.checkForOrderNum(orderNumOfEdit, ordersToDisplay);

            String currentName = currentOrder.getCustomerName();
            String editedName = view.displayCurrentNameGetUpdate(currentName);

            List<Tax> currentTaxInfo = service.getCurrentTaxInfo();
            view.displayValidStates(currentTaxInfo);
            String currentState = currentOrder.getState();
            String editedState = "Invalid State";
            boolean validState = false;
            while (!validState && !(editedState.equals("".trim()))) {
                editedState = view.displayCurrentStateGetUpdate(currentState);
                validState = service.validateStateSelection(editedState);
            }
            
            List<Product> currentProdInfo = service.getCurrentProdInfo();
            view.displayValidProducts(currentProdInfo);
            String currentProductType = currentOrder.getProductType();
            String editedProductType = "Invalid Product";
            boolean validProductType = false;
            while(!validProductType && !(editedProductType.equals("".trim()))){
                editedProductType = view.displayCurrentProductGetUpdate(currentProductType);
                validProductType = service.validateProdSelection(editedProductType);
            }
            
            BigDecimal currentArea = currentOrder.getArea();
            BigDecimal editedArea = view.displayCurrentAreaGetUpdate(currentArea);

           service.editAnOrder(currentOrder, editedName, editedState, editedProductType, editedArea);
            
            view.displayOrderUpdatedBanner();

        } catch (StateNotFoundException | ProductsDaoException | TaxesDaoException | OrderDaoException | OrderNumNotFoundException | OrderNotFoundForDateException | ProductNotFoundException ex) {
            view.displayErrorMessageBanner(ex.getMessage());
        } 

    }

    private void removeOrder() {
        try {
            view.displayRemoveOrderBanner();
            LocalDate orderDateOfDelete = view.getOrderDateOfOrderToDelete();
            List<Order> ordersToDisplay = service.getNonzeroOrdersByDate(orderDateOfDelete);

            int orderNumOfDelete = 0;
            orderNumOfDelete = view.getOrderNumberOfOrderToDelete();
            Order orderToReturn = service.checkForOrderNum(orderNumOfDelete, ordersToDisplay);
            view.displayInfoToBeDeleted(orderToReturn);

            String commitToDelete = view.getDeleteOrderCommitResponse();
            if (commitToDelete.toLowerCase().equals("y")) {

               service.removeCustomerOrder(orderDateOfDelete, orderNumOfDelete);
                view.displayOrderDeletedBanner();

            } else {

                view.OrderDeleteAbandonedBanner();
            }

        } catch (OrderDaoException | OrderNumNotFoundException | OrderNotFoundForDateException ex) {
            view.displayErrorMessageBanner(ex.getMessage());
        }

    }

}
