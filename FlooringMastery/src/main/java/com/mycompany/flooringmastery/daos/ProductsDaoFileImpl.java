/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.daos;

import com.mycompany.flooringmastery.dtos.Product;
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
public class ProductsDaoFileImpl implements ProductsDao {

    String filePath;

    public ProductsDaoFileImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Product> getProductInfo() throws ProductsDaoException {

        List<Product> currentProdInfo = new ArrayList<>();

        FileReader reader = null;

        try {
            reader = new FileReader(filePath);
            Scanner newScn = new Scanner(reader);
            newScn.nextLine();

            while (newScn.hasNextLine()) {

                String line = newScn.nextLine();
                if (line.length() > 0) {

                    String[] fields = line.split(",");
                    Product toAdd = new Product();
                    toAdd.setProductType(fields[0]);
                    toAdd.setMatCostPerSquareFoot(new BigDecimal(fields[1]));
                    toAdd.setLaborCostPerSquareFoot(new BigDecimal(fields[2]));

                    currentProdInfo.add(toAdd);
                }

            }

        } catch (FileNotFoundException ex) {
            throw new ProductsDaoException("Error: A products file was not found.");
        } finally {
            try {
                if (reader != null) {

                    reader.close();
                }
            } catch (IOException ex) {
                throw new ProductsDaoException("Error: Could not close writer.");
            }

        }

        return currentProdInfo;
    }

    @Override
    public Product getProductInfoByName(String custProd) throws ProductsDaoException {

        List<Product> allProducts = getProductInfo();
        Product toReturn = new Product();

        for (Product prod : allProducts) {

            if (prod.getProductType().equalsIgnoreCase(custProd)) {

                toReturn = prod;

            }

        }
        

        return toReturn;
    }

}
