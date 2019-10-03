/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.ui;

import java.math.BigDecimal;

/**
 *
 * @author Chad
 */
public interface UserIO {

    void print(String msg);

    void printSameLine(String msg);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);

    String readString(String prompt);

    public BigDecimal readBigDecimal(String prompt);

    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max);

}
