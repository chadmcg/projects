/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumber.latest.service;

import com.mycompany.guessthenumber.latest.dao.Dao;
import com.mycompany.guessthenumber.latest.dao.DaoException;
import com.mycompany.guessthenumber.latest.models.Game;
import com.mycompany.guessthenumber.latest.models.Round;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Chad
 */
@Component
public class Service {
    
//     @Autowired
//     Dao gameDao;
     
     Dao gameDao;
     
     @Autowired
     public Service(Dao gameDao){
         
         this.gameDao = gameDao;
     }

    public List<Game> displayAllGames() {

        //Passthrough method; retrieval happens in the dao
        return gameDao.getAllGames();

    }

    public int setUpNewGame() {

        //Create a new number for the player to guess
        String targetNum = createNewTargetNumber();

        //Pass new number to the dao. Dao will add a new game record to the DB
        //Dao returns an id for the game
        return gameDao.addNewGame(targetNum);

    }

    public String createNewTargetNumber() {

        //Target id: 4 digits; each digit between 0 and 9; no repeated digits
        List<String> possibleDigits = new ArrayList<>();

        possibleDigits.add("0");
        possibleDigits.add("1");
        possibleDigits.add("2");
        possibleDigits.add("3");
        possibleDigits.add("4");
        possibleDigits.add("5");
        possibleDigits.add("6");
        possibleDigits.add("7");
        possibleDigits.add("8");
        possibleDigits.add("9");

        Random rnd = new Random();

        String targetNum = "";

        for (int i = 0; i < 4; i++) {

            int arrayIndex = rnd.nextInt(possibleDigits.size());
            targetNum += possibleDigits.get(arrayIndex);
            possibleDigits.remove(arrayIndex);

        }

        return targetNum;

    }

    public List<Game> displaySingleGame(int gameId) throws GameNotFoundException {
        
         return gameDao.getSingleGame(gameId);
    }

    public List<Round> displayRoundsForGame(int gameId) {

        return gameDao.getRoundsForGame(gameId);

    }

    public List<Round> evaluateGuessedNumber(Round newRound) throws InvalidGuessException, GameNotFoundException {

        //Get the target # for the game from dao
        String targetNum = gameDao.getSingleGameNoSuppression(newRound.getGameId()).get(0).getTargetNum();

        //Get the guess # for the game from round object
        String guessNum = newRound.getGuessNum();

        //Determine whether the guessed number is valid
        boolean validGuess = validateGuess(guessNum);
        
        //If guess is not valid, throw exception
        if(!validGuess){
            throw new InvalidGuessException("The guessed number is not valid");
        }

        //Calculate the number of partial and exact matches
        int exactMatchCt = 0;
        int partialMatchCt = 0;

        for (int i = 0; i < 4; i++) {

            if (targetNum.charAt(i) == guessNum.charAt(i)) {

                exactMatchCt++;

            } else {

                for (int j = 0; j < 4; j++) {

                    if (targetNum.charAt(i) == guessNum.charAt(j) && i != j) {

                        partialMatchCt++;

                    }
                }
            }

            //Assign a round id to the round object(current max for the game plus one).
            List<Round> roundsForGame = gameDao.getRoundsForGame(newRound.getGameId());
            int maxRound = 1;

            if (roundsForGame.size() == 0) {

                newRound.setRoundId(maxRound);
            } else {
                for (Round toCheck : roundsForGame) {

                    if (toCheck.getRoundId() > maxRound) {

                        maxRound = toCheck.getRoundId();

                    }
                }
                newRound.setRoundId(maxRound + 1);
            }
            
            //Create a timestamp for the user's guess
            LocalDateTime currentLDT = LocalDateTime.now();
            Timestamp currentTS = Timestamp.valueOf(currentLDT);

            //Update the round object
            newRound.setExactMatchCt(exactMatchCt);
            newRound.setGuessNum(guessNum);
            newRound.setGuessTime(currentTS);
            newRound.setExactMatchCt(exactMatchCt);
            newRound.setPartialMatchCt(partialMatchCt);

        }

        //Pass the updated round object to the dao to have it update the round
        //table
        gameDao.addNewRound(newRound);

        // if the user guessed the correct number, update the game table to 
        // set the isOver variable to true
        if (exactMatchCt == 4) {

            gameDao.updateGameStatus(newRound.getGameId());

        }

        //Return the updated round object
        List<Round> newRounds = new ArrayList<>();
        Round r = new Round();
        r.setGuessNum(targetNum);
        newRounds.add(newRound);
        return newRounds;
    }

    private boolean validateGuess(String guessNum) {

        boolean validGuess = true;
        
        //Guess must be a number
        for(int i = 0; i < guessNum.length(); i++){
            
           if(!Character.isDigit(guessNum.charAt(i))){
               
               validGuess = false;
           }
            
        }
        

        //Number must be four digits
        if (guessNum.length() != 4) {
            validGuess = false;
        } 

        //Number cannot have any repeats
        for (int i = 0; i < guessNum.length(); i++) {

            for (int j = 0; j < guessNum.length(); j++) {

                if (guessNum.charAt(i) == guessNum.charAt(j) && i != j) {

                    validGuess = false;
                }

            }

        }

        return validGuess;

    }
}
