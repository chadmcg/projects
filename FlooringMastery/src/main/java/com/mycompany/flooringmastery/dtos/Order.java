/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dtos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 *
 * @author Chad
 */
public class Order {
    
    LocalDate orderDate;
    int orderNumber;
    String customerName;
    String state;
    BigDecimal taxRate;
    String productType;
    BigDecimal area;
    BigDecimal matCostPerSquareFoot;
    BigDecimal laborCostPerSquareFoot;
    BigDecimal totalMatCost;
    BigDecimal totalLaborCost;
    BigDecimal totalTax;
    BigDecimal totalCost;

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getMatCostPerSquareFoot() {
        return matCostPerSquareFoot;
    }

    public void setMatCostPerSquareFoot(BigDecimal matCostPerSquareFoot) {
        this.matCostPerSquareFoot = matCostPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public BigDecimal getTotalMatCost() {
        
        return area.multiply(matCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
    }

//    public void setTotalMatCost(BigDecimal totalMatCost) {
//        this.totalMatCost = totalMatCost;
//    }

    public BigDecimal getTotalLaborCost() {
        
        return area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
    }

//    public void setTotalLaborCost(BigDecimal totalLaborCost) {
//        this.totalLaborCost = totalLaborCost;
//    }

    public BigDecimal getTotalTax() {
        
        return ((area.multiply(matCostPerSquareFoot)).add(area.multiply(laborCostPerSquareFoot))).multiply(taxRate).divide(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        
    }

//    public void setTotalTax(BigDecimal totalTax) {
//        this.totalTax = totalTax;
//    }

    public BigDecimal getTotalCost() {
        
        return area.multiply(matCostPerSquareFoot).add(area.multiply(laborCostPerSquareFoot)).add(((area.multiply(matCostPerSquareFoot)).add(area.multiply(laborCostPerSquareFoot))).multiply(taxRate).divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
        
    }

//    public void setTotalCost(BigDecimal totalCost) {
//        this.totalCost = totalCost;
//    }
    
    

    
    
    
}
