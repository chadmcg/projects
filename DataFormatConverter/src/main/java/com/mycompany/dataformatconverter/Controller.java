/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataformatconverter;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chad
 */
public class Controller {

    FileDao fDao;
    View converterView;

    public Controller(FileDao fDao, View converterView) {

        this.fDao = fDao;
        this.converterView = converterView;
    }

    public void convertFormatting() {

        try {

            List<Item> itemsInCSV = fDao.getItemsFromCSV();
            fDao.convertCSVtoXML(itemsInCSV);

        } catch (FileDaoException e) {

            converterView.printErrorMessage(e.getMessage());

        }

    }

}
