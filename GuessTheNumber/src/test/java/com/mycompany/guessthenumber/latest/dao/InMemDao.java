/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumber.latest.dao;

import com.mycompany.guessthenumber.latest.models.Game;
import com.mycompany.guessthenumber.latest.models.Round;
import com.mycompany.guessthenumber.latest.service.GameNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chad
 */
@Repository
@Profile("ServiceTest")
public class InMemDao implements Dao {
    
    Game g1 = new Game();
    Game g2 = new Game();
    
    List<Game> gameList = new ArrayList<>();; 
    
    Round g1R1 = new Round();
    Round g1R2 = new Round();
    Round g1R3 = new Round();
    
    Round g2R1 = new Round();
    Round g2R2 = new Round();
    Round g2R3 = new Round();
    
    List<Round> g1RoundList = new ArrayList<>();
    List<Round> g2RoundList = new ArrayList<>();
    
    public InMemDao(){
        
        g1.setGameId(1);
        g1.setTargetNum("1234");
        g1.setIsOver(false);
        gameList.add(g1);
        
        g2.setGameId(2);
        g2.setTargetNum("9876");
        g2.setIsOver(true);
        gameList.add(g2);
        
        g1R1.setRoundId(1);
        g1R1.setGameId(1);
        g1R1.setGuessNum("2150");
        g1R1.setGuessTime(Timestamp.valueOf(LocalDateTime.now()));
        g1R1.setPartialMatchCt(2);
        g1R1.setExactMatchCt(0);
        g1RoundList.add(g1R1);
        
        g1R2.setRoundId(2);
        g1R2.setGameId(1);
        g1R2.setGuessNum("1248");
        g1R2.setGuessTime(Timestamp.valueOf(LocalDateTime.now()));
        g1R2.setPartialMatchCt(1);
        g1R2.setExactMatchCt(2);
        g1RoundList.add(g1R2);
        
        g1R3.setRoundId(3);
        g1R3.setGameId(1);
        g1R3.setGuessNum("1237");
        g1R3.setGuessTime(Timestamp.valueOf(LocalDateTime.now()));
        g1R3.setPartialMatchCt(0);
        g1R3.setExactMatchCt(3);
        g1RoundList.add(g1R3);
        
        g2R1.setRoundId(1);
        g2R1.setGameId(2);
        g2R1.setGuessNum("1095");
        g2R1.setGuessTime(Timestamp.valueOf(LocalDateTime.now()));
        g2R1.setPartialMatchCt(1);
        g2R1.setExactMatchCt(0);
        g2RoundList.add(g2R1);
        
        g2R2.setRoundId(2);
        g2R2.setGameId(2);
        g2R2.setGuessNum("9867");
        g2R2.setGuessTime(Timestamp.valueOf(LocalDateTime.now()));
        g2R2.setPartialMatchCt(2);
        g2R2.setExactMatchCt(2);
        g2RoundList.add(g2R2);
        
        g2R3.setRoundId(3);
        g2R3.setGameId(2);
        g2R3.setGuessNum("9876");
        g2R3.setGuessTime(Timestamp.valueOf(LocalDateTime.now()));
        g2R3.setPartialMatchCt(0);
        g2R3.setExactMatchCt(4);
        g2RoundList.add(g2R3);
        
    }

    @Override
    public List<Game> getAllGames() {

        return gameList;
    }

    @Override
    public int addNewGame(String targetNum) {
        
       Game newGame = new Game();
       
       int currentMaxId = 0;
       for(Game g: gameList){
           
           if(g.getGameId()>currentMaxId){
               currentMaxId = g.getGameId();
           }
           
       }
       
       int gameId = currentMaxId + 1;
       
       newGame.setGameId(gameId);
       newGame.setTargetNum(targetNum);
       newGame.setIsOver(false);
       
       gameList.add(newGame);

       return gameId;
        
    }

    @Override
    public List<Game> getSingleGame(int gameId) throws GameNotFoundException {
        
       List<Game> singleGame = new ArrayList<>();
        
       for (Game g : gameList){
           
           if(g.getGameId()==gameId){
               
               if(g.isIsOver()==false){
                   
                   g.setTargetNum("-");
                   
               }
               
               singleGame.add(g);
               
           }    
           
       }
       
       if(singleGame.isEmpty()){
           throw new GameNotFoundException("Game was not found for the provided id.");
       }
       
       return singleGame;
        
    }

    @Override
    public List<Round> getRoundsForGame(int gameId) {
        
        List<Round> roundList = new ArrayList<>();
        
        if(gameId ==1){
            roundList = g1RoundList;
        } 
        
        if(gameId ==2){
            roundList = g2RoundList;
        } 
        
        return roundList;
        
    }

    @Override
    public void addNewRound(Round newRound) {
        
        if(newRound.getGameId()==1){
            g1RoundList.add(newRound);
        }
        
        if(newRound.getGameId()==2){
            g2RoundList.add(newRound);
        }
        
    }

    @Override
    public void updateGameStatus(int gameId) {
        
        if(gameId == 1){
            g1.setIsOver(true);
        }
        
        if(gameId == 2){
            g2.setIsOver(true);
        }
        
    }

    @Override
    public List<Game> getSingleGameNoSuppression(int gameId) {
        List<Game> singleGame = new ArrayList<>();
        
       for (Game g : gameList){
           
           if(g.getGameId()==gameId){
                  
               singleGame.add(g);
           }    
           
       }
       
       return singleGame;
    }

    @Override
    public void deleteDataFromDB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
