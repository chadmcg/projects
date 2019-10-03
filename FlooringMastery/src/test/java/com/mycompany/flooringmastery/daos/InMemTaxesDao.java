/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.dtos.Tax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chad
 */
public class InMemTaxesDao implements TaxesDao {
    
    Tax tax1;
    Tax tax2;
    Tax tax3;
    
    List<Tax> taxList = new ArrayList<>();
    
    public InMemTaxesDao(){
        
        tax1 = new Tax();
        tax1.setState("MN");
        tax1.setTaxRate(new BigDecimal("8.25"));
        taxList.add(tax1);
        
        tax2 = new Tax();
        tax2.setState("WI");
        tax2.setTaxRate(new BigDecimal("9.25"));
        taxList.add(tax2);    
        
        tax3 = new Tax();
        tax3.setState("ND");
        tax3.setTaxRate(new BigDecimal("10.25"));
        taxList.add(tax3);
        
    }

    
    

    @Override
    public List<Tax> getTaxInfo() throws TaxesDaoException {
        return taxList;
    }

    @Override
    public Tax getTaxInfoByName(String custState) throws TaxesDaoException {
         List<Tax> allTaxInfo = getTaxInfo();
        Tax toReturn = new Tax();

        for (Tax tax1 : allTaxInfo) {

            if (tax1.getState().equalsIgnoreCase(custState)) {

                toReturn = tax1;
            }

        }

        return toReturn;
    }
    
    
}
