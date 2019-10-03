/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.dtos.Tax;
import java.util.List;

/**
 *
 * @author Chad
 */
public interface TaxesDao {
    
    List<Tax> getTaxInfo() throws TaxesDaoException;

    public Tax getTaxInfoByName(String custState) throws TaxesDaoException;
    
}
