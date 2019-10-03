/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dto;

import java.math.BigDecimal;

/**
 *
 * @author Chad
 */
public class Change {

    int dollars;
    int quarters;
    int dimes;
    int nickels;
    int pennies;

    public Change(BigDecimal changeAmt) {

        changeAmt = changeAmt.multiply(new BigDecimal("100"));
        int currentPennies = changeAmt.intValueExact();
        dollars = currentPennies / 100;
        currentPennies -= dollars * 100;
        quarters = currentPennies / 25;
        currentPennies -= quarters * 25;
        dimes = currentPennies / 10;
        currentPennies -= dimes * 10;
        nickels = currentPennies / 5;
        currentPennies -= nickels * 5;
        pennies = currentPennies;

    }

    public int getDollars() {
        return dollars;
    }

    public int getQuarters() {
        return quarters;
    }

    public int getDimes() {
        return dimes;
    }

    public int getNickels() {
        return nickels;
    }

    public int getPennies() {
        return pennies;
    }

}
