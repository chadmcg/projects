/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.dtos.Product;
import com.mycompany.flooringmastery.dtos.Tax;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chad
 */
public class TaxesDaoFileImpl implements TaxesDao {

    String filePath;

    public TaxesDaoFileImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Tax> getTaxInfo() throws TaxesDaoException {

        List<Tax> currentTaxInfo = new ArrayList<>();

        FileReader reader = null;

        try {
            reader = new FileReader(filePath);
            Scanner newScn = new Scanner(reader);
            newScn.nextLine();

            while (newScn.hasNextLine()) {

                String line = newScn.nextLine();
                if (line.length() > 0) {
                    String[] fields = line.split(",");
                    Tax toAdd = new Tax();
                    toAdd.setState(fields[0]);
                    toAdd.setTaxRate(new BigDecimal(fields[1]));

                    currentTaxInfo.add(toAdd);

                }

            }

        } catch (FileNotFoundException ex) {
            throw new TaxesDaoException("Error: A tax file was not found.");
        } finally {

            try {
                if (reader != null) {

                    reader.close();
                }
            } catch (IOException ex) {
                throw new TaxesDaoException("Error: Could not closer writer.");
            }
        }

        return currentTaxInfo;

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
