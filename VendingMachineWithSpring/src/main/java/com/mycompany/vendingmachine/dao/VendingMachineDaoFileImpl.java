/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;

import com.mycompany.vendingmachine.dto.Item;
import com.mycompany.vendingmachine.service.VendingMachineInsufficientFundsException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Chad
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {

    String filePath;

    public VendingMachineDaoFileImpl(String filePath) {

        this.filePath = filePath;
    }

    @Override
    public List<Item> getInventory() throws VendingMachineDaoException {

        List<Item> itemToReturn = new ArrayList<>();

        FileReader reader = null;

        try {
            reader = new FileReader(filePath);
            Scanner newScn = new Scanner(reader);

            while (newScn.hasNextLine()) {

                String line = newScn.nextLine();
                if (line.length() > 0) {

                    String[] fields = line.split(",");

                    Item toAdd = new Item();
                    toAdd.setItemID(Integer.parseInt(fields[0]));
                    toAdd.setItemName(fields[1]);
                    toAdd.setCurrentInventory(Integer.parseInt(fields[2]));
                    toAdd.setItemPrice(new BigDecimal(fields[3]));

                    itemToReturn.add(toAdd);

                }

            }
        } catch (FileNotFoundException ex) {
            throw new VendingMachineDaoException("Error: Could not read from " + filePath);
        } finally {
            try {
                if (reader != null) {

                    reader.close();
                }

            } catch (IOException ex) {

            }

        }

        return itemToReturn;
    }

    @Override
    public void updateInventory(int itemID) throws VendingMachineInsufficientFundsException, VendingMachineDaoException {

        List<Item> allItems = getInventory();

        for (Item currentItem : allItems) {
            if (currentItem.getItemID() == itemID) {
                currentItem.setCurrentInventory(currentItem.getCurrentInventory() - 1);
            }
        }

        writeFile(allItems);

    }

    @Override
    public void writeFile(List<Item> allItems) throws VendingMachineInsufficientFundsException, VendingMachineDaoException {

        FileWriter writer = null;

        try {
            writer = new FileWriter(filePath);
        } catch (IOException ex) {
            throw new VendingMachineInsufficientFundsException("ERROR: Could not write to " + filePath);
        }
        PrintWriter pw = new PrintWriter(writer);

        for (Item toWrite : allItems) {
            String line = convertToLine(toWrite);
            pw.println(line);
        }

        try {
            writer.close();
        } catch (IOException ex) {
            throw new VendingMachineInsufficientFundsException("ERROR: Could not close writer for " + filePath);
        }

    }

    private String convertToLine(Item toWrite) {

        String line
                = toWrite.getItemID() + ","
                + toWrite.getItemName() + ","
                + toWrite.getCurrentInventory() + ","
                + toWrite.getItemPrice();

        return line;
    }

    @Override
    public List<Item> getNonzeroItems() throws VendingMachineDaoException {
        List<Item> currentInventory;

        currentInventory = getInventory();

        List<Item> nonzeroInventory = new ArrayList<>();

        for (int i = 0; i < currentInventory.size(); i++) {

            Item toCheck = currentInventory.get(i);

            if (toCheck.getCurrentInventory() > 0) {

                nonzeroInventory.add(toCheck);

            }

        }

        return nonzeroInventory;
    }
}
