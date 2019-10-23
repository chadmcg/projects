/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumber.latest.dao;

import com.mycompany.guessthenumber.latest.models.Game;
import com.mycompany.guessthenumber.latest.models.Round;
import com.mycompany.guessthenumber.latest.service.GameNotFoundException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Chad
 */
public interface Dao {
    
    List<Game> getAllGames();

    public int addNewGame(String targetNum);

    public List<Game> getSingleGame(int gameId) throws GameNotFoundException;

    public List<Round> getRoundsForGame(int gameId);

    public void addNewRound(Round newRound);

    public void updateGameStatus(int gameId);

    public List<Game> getSingleGameNoSuppression(int gameId) throws GameNotFoundException;
    
    public void deleteDataFromDB();
   
}
