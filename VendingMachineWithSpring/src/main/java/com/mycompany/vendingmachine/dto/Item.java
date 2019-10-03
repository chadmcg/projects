/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Chad
 */
public class Item {

    int itemID;
    String itemName;
    int currentInventory;
    BigDecimal itemPrice;

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCurrentInventory(int currentInventory) {
        this.currentInventory = currentInventory;
    }

    public int getCurrentInventory() {
        return currentInventory;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.itemID;
        hash = 97 * hash + Objects.hashCode(this.itemName);
        hash = 97 * hash + this.currentInventory;
        hash = 97 * hash + Objects.hashCode(this.itemPrice);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.itemID != other.itemID) {
            return false;
        }
        if (this.currentInventory != other.currentInventory) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        if (!Objects.equals(this.itemPrice, other.itemPrice)) {
            return false;
        }
        return true;
    }
    
    
    

}
