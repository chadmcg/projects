/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dtos;

import java.math.BigDecimal;

/**
 *
 * @author Chad
 */
public class Product {
    
    String productType;
    BigDecimal matCostPerSquareFoot;
    BigDecimal laborCostPerSquareFoot;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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
    
    
  
    
}
