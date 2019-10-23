/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumber.latest.models;

import java.sql.Timestamp;

/**
 *
 * @author Chad
 */
public class Round {
    
    int gameId;
    int roundId;
    String guessNum;
    Timestamp guessTime;
    int exactMatchCt;
    int partialMatchCt;

    
    public Timestamp getGuessTime() {
        return guessTime;
    }

    public void setGuessTime(Timestamp guessTime) {
        this.guessTime = guessTime;
    }
    
    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGuessNum() {
        return guessNum;
    }

    public void setGuessNum(String guessNum) {
        this.guessNum = guessNum;
    }

    public int getExactMatchCt() {
        return exactMatchCt;
    }

    public void setExactMatchCt(int exactMatchCt) {
        this.exactMatchCt = exactMatchCt;
    }

    public int getPartialMatchCt() {
        return partialMatchCt;
    }

    public void setPartialMatchCt(int partialMatchCt) {
        this.partialMatchCt = partialMatchCt;
    }
    
    
}
