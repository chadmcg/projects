/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataformatconverter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
public class FileDao {

    public List<Item> getItemsFromCSV() throws FileDaoException {

        List<Item> itemsFromCSV = new ArrayList<>();

        FileReader reader = null;

        try {

            reader = new FileReader("Items-CommaSeparated.txt");

            Scanner newScanner = new Scanner(reader);

            while (newScanner.hasNextLine()) {

                String itemLine = newScanner.nextLine();
                if (itemLine.length() > 0) {

                    String[] itemAttributes = itemLine.split(",");

                    Item toAdd = new Item();
                    toAdd.setName(itemAttributes[0]);
                    toAdd.setQuantity(Integer.parseInt(itemAttributes[1]));
                    toAdd.setSku(itemAttributes[2].replace("SKU#", ""));
                    toAdd.setPrice(new BigDecimal(itemAttributes[3].replace("$", "")));

                    itemsFromCSV.add(toAdd);

                }

            }

            return itemsFromCSV;
        } catch (FileNotFoundException ex) {
            throw new FileDaoException("Error: Could not locate file to read from.");
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    throw new FileDaoException("Error: Could not close reader.");
                }
            }
        }

    }

    public void convertCSVtoXML(List<Item> itemsInCSV) throws FileDaoException {

        FileWriter writer;
        try {
            writer = new FileWriter("Items-XML.txt");
        } catch (IOException ex) {
            throw new FileDaoException("Error: Could not write to file.");
        }

        PrintWriter pw = new PrintWriter(writer);

        for (Item itemInCSV : itemsInCSV) {

            String line = "<Item>\n";
            line += "   <Name>" + itemInCSV.getName() + "</Name>\n";;
            line += "   <Quantity>" + itemInCSV.getQuantity() + "</Quantity>\n";
            line += "   <SKU>" + itemInCSV.getSku() + "</SKU>\n";
            line += "   <Price>" + itemInCSV.getPrice() + "</Price>\n";
            line += "</Item>";

            pw.println(line);

        }

        try {
            writer.close();
        } catch (IOException ex) {
            throw new FileDaoException("Error: Could not close writer.");
        }

    }

}
