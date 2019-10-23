/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumber.latest.controller;


import com.mycompany.guessthenumber.latest.dao.Dao;
import com.mycompany.guessthenumber.latest.dao.DaoException;
import com.mycompany.guessthenumber.latest.models.Game;
import com.mycompany.guessthenumber.latest.models.Round;
import com.mycompany.guessthenumber.latest.service.GameNotFoundException;
import com.mycompany.guessthenumber.latest.service.InvalidGuessException;
import com.mycompany.guessthenumber.latest.service.Service;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chad
 */

@RestController
@RequestMapping("/guessTheNumber")
public class Controller {
    
    @Autowired
    Service gameService;
    
    @GetMapping("/games")
    public List<Game> displayAllGames(){
        
        return gameService.displayAllGames();
        
    }
    
    
    @PostMapping("/begin")
    public String beginNewGame(){
        
        int gameId = gameService.setUpNewGame();
        
        return "A new game has begun! The id for this game is: " + gameId;
        
    }
    
    @PostMapping("/guess")
    public List<Round> guessNumber(@RequestBody Round newRound) throws InvalidGuessException, GameNotFoundException{
 
//         Form: 
//        {
//        "gameId": 1,
//        "guessNum": "1234"
//          }
        
        return gameService.evaluateGuessedNumber(newRound);
        
        
    }
    
     @GetMapping("/game/{gameId}")
    public List<Game> displaySingleGame(@PathVariable int gameId) throws GameNotFoundException{
        
        return gameService.displaySingleGame(gameId);
        
    }
    
    @GetMapping("/rounds/{gameId}")
    public List<Round> displayRoundsForGame(@PathVariable int gameId){
        
        return gameService.displayRoundsForGame(gameId);
    }
    
    
}
