/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumber.latest.service;

import com.mycompany.guessthenumber.latest.TestApplicationConfiguration;
import com.mycompany.guessthenumber.latest.dao.InMemDao;
import com.mycompany.guessthenumber.latest.models.Game;
import com.mycompany.guessthenumber.latest.models.Round;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Chad
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@ActiveProfiles(profiles = "ServiceTest")
public class ServiceTest {
    
    public ServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of displayAllGames method, of class Service.
     */
    @Test
    public void testDisplayAllGames() {
        
        //Initialize
        Service testService = new Service(new InMemDao());
        
        //The InMemDao has two games
        //The DisplayAllGames method returns all games
        
        //Verify that the correct number of games is returned
        List<Game> allGames = testService.displayAllGames();
        assertEquals(2, allGames.size());
        
        //Verify that the properties of the first game are correct
        Game testGameOne = allGames.get(0);
        
        assertEquals(1, testGameOne.getGameId());
        assertEquals("1234", testGameOne.getTargetNum());
        assertEquals(false, testGameOne.isIsOver());
        
        //Verify that the properties of the second game are correct
        Game testGameTwo = allGames.get(1);
        
        assertEquals(2, testGameTwo.getGameId());
        assertEquals("9876", testGameTwo.getTargetNum());
        assertEquals(true, testGameTwo.isIsOver());
        
    }

    /**
     * Test of setUpNewGame method, of class Service.
     */
    @Test
    public void testSetUpNewGame() {
        
        //Initialize
        Service testService = new Service(new InMemDao());
        
        //Retrieve a list of all of the games in the InMemDao
        //The InMemDao is initialized to start with two games
        List<Game> allGames = testService.displayAllGames();
        
        //The SetUpNewGame method takes in no parameters. It generates
        //a new target number and passes that it the dao. The dao adds 
        //a new game to the DB and returns a gameID;
        
        //Call the SetUpNewGame method
        int newGameId = testService.setUpNewGame();
        
        //Verify that the new number of games is correct
        List<Game> allGamesAfterAdd = testService.displayAllGames();
        assertEquals(3, allGamesAfterAdd.size());
        
        //verify that the properties of the new game are correct
        assertEquals(newGameId, allGamesAfterAdd.get(2).getGameId());
        assertEquals(false, allGamesAfterAdd.get(2).isIsOver());
        
    }
    
    @Test
    public void testCreateNewTargetNumber(){
        
         //Initialize
        Service testService = new Service(new InMemDao());
        
        
        //Verify that the target number created is valid
        //Since target is randomly generated, test is repeated multiple times
        //to strengthen result
        for (int i = 0; i < 50; i++) {

            String targetNumTest = testService.createNewTargetNumber();

            //Validate that the target number generated is valid
            Boolean validGuess = validateGuess(targetNumTest);
            
            //If the target number generated is not valid then test should fail
            if(!validGuess){
                fail("An invalid target number was generated.");
            }  
        }
        
    }

    /**
     * Test of displaySingleGame method, of class Service.
     */
    @Test
    public void testDisplaySingleGameWhereGameIsInProgress() {
        
        try {
            //Initialize
            Service testService = new Service(new InMemDao());
            
            //The displaySingleGame method will return information for a single
            //game. If the game is in progress, the target number is suppressed.
            //If the game is over, the target number is displayed.
            
            //Game 1 in the InMemDao is not over
            //Verify that the attributes returned are correct
            List<Game> testGame = testService.displaySingleGame(1);
            
            assertEquals(1, testGame.get(0).getGameId());
            assertEquals("-", testGame.get(0).getTargetNum());
            assertEquals(false, testGame.get(0).isIsOver());
        } catch (GameNotFoundException ex) {
            fail("It is not expected that an exception will be thrown.");
        }
        
    }
    
    @Test
    public void testDisplaySingleGameWhereGameIsOver() {
        
        try {
            //Initialize
            Service testService = new Service(new InMemDao());
            
            //The displaySingleGame method will return information for a single
            //game. If the game is in progress, the target number is suppressed.
            //If the game is over, the target number is displayed.
            
            //Game 2 in the InMemDao is over
            //Verify that the attributes returned are correct
            List<Game> testGame = testService.displaySingleGame(2);
            
            assertEquals(2, testGame.get(0).getGameId());
            assertEquals("9876", testGame.get(0).getTargetNum());
            assertEquals(true, testGame.get(0).isIsOver());
        } catch (GameNotFoundException ex) {
            fail("It is not expected that an exception will be thrown.");
        }  
    }
    
    @Test
    public void testDisplaySingleGameWhereGameIdIsInvalid() {
        
        try {
            //Initialize
            Service testService = new Service(new InMemDao());
            
            //The displaySingleGame method will return information for a single
            //game. If the game is in progress, the target number is suppressed.
            //If the game is over, the target number is displayed.
            
            //There is not a game with id of 1000 in the InMemDao
            List<Game> testGame = testService.displaySingleGame(1000);
            fail("It is expected that a GameNotFoundException will be thrown");
            
        } catch (GameNotFoundException ex) {
            //It is expected that a GameNotFoundException will be thrown
        }  
    }

    /**
     * Test of displayRoundsForGame method, of class Service.
     */
    @Test
    public void testDisplayRoundsForGame() {
        
        //Initialize
        Service testService = new Service(new InMemDao());
        
        //The displayRoundsForGame method returns all rounds for a given game
        
        //Return the rounds for game 1 from the InMemDao
        List<Round> roundTest1 = testService.displayRoundsForGame(1);
        
        //Verify that the correct number of rounds is returned
        assertEquals(3, roundTest1.size());
        
        //Verify that the attributes of the first round are correct. (Time attribute is
        //skipped.)
        assertEquals(1, roundTest1.get(0).getRoundId());
        assertEquals(1, roundTest1.get(0).getGameId());
        assertEquals("2150", roundTest1.get(0).getGuessNum());
        assertEquals(2, roundTest1.get(0).getPartialMatchCt());
        assertEquals(0, roundTest1.get(0).getExactMatchCt());
        
        //Verify that the attributes of the last round are correct. (Time attribute is
        //skipped.)
        assertEquals(3, roundTest1.get(2).getRoundId());
        assertEquals(1, roundTest1.get(2).getGameId());
        assertEquals("1237", roundTest1.get(2).getGuessNum());
        assertEquals(0, roundTest1.get(2).getPartialMatchCt());
        assertEquals(3, roundTest1.get(2).getExactMatchCt());
        
    }

    /**
     * Test of evaluateGuessedNumber method, of class Service.
     */
    
    @Test
    public void testEvaluateGuessedNumberGoldenPathIncorrectGuess() {
        
        try {
            //Initialize
            Service testService = new Service(new InMemDao());
            
            //The evaluateGuessedNumber method takes in a round object as a parameter.
            //The method retrieves the target # for the identified game from the dao. 
            //It determines whether the guess is valid.
            //If the guess is valid, the number of partial and exact matches is calculated.
            //The round object is updated with new attributes (id, timestamp, match ct, etc.)
            //If the user guessed the correct # then the gameStatus is called to update the
            //game table
            //A round object is returned
            
            Round newRound = new Round();
            
            //Create a new round object with a game id and a guess
            newRound.setGameId(1);
            newRound.setGuessNum("0243");
            
            //Call the evaluateGuessedNumberMethod
            List<Round> updatedRoundList = testService.evaluateGuessedNumber(newRound);
            
            //Verify that the attributes of the returned round are correct. (Time 
            //attribute is correct.)
            Round updatedRound = updatedRoundList.get(0);
            
            assertEquals(1, updatedRound.getGameId());
            assertEquals("0243", updatedRound.getGuessNum());
            assertEquals(1, updatedRound.getExactMatchCt());
            assertEquals(2, updatedRound.getPartialMatchCt());
            
            //Verify that the attributes of the relevant game are correct
            //Because the guess was not correct, the game should still be in progress
            List<Game> associatedGame = testService.displaySingleGame(1);
            assertEquals(false, associatedGame.get(0).isIsOver());
            
            
        } catch (InvalidGuessException | GameNotFoundException ex) {
           fail("It is not expected that an exception will be thrown.");
        }
          
    }  
    
    @Test
    public void testEvaluateGuessedNumberGoldenPathCorrectGuess() {
        
        try {
            //Initialize
            Service testService = new Service(new InMemDao());
            
            //The evaluateGuessedNumber method takes in a round object as a parameter.
            //The method retrieves the target # for the identified game from the dao. 
            //It determines whether the guess is valid.
            //If the guess is valid, the number of partial and exact matches is calculated.
            //The round object is updated with new attributes (id, timestamp, match ct, etc.)
            //If the user guessed the correct # then the gameStatus is called to update the
            //game table
            //A round object is returned
            
            Round newRound = new Round();
            
            //Create a new round object with a game id and a guess
            newRound.setGameId(1);
            newRound.setGuessNum("1234");
            
            //Call the evaluateGuessedNumberMethod
            List<Round> updatedRoundList = testService.evaluateGuessedNumber(newRound);
            
            //Verify that the attributes of the returned round are correct. (Time 
            //attribute is correct.)
            Round updatedRound = updatedRoundList.get(0);
            
            assertEquals(1, updatedRound.getGameId());
            assertEquals("1234", updatedRound.getGuessNum());
            assertEquals(4, updatedRound.getExactMatchCt());
            assertEquals(0, updatedRound.getPartialMatchCt());
            
            //Verify that the attributes of the relevant game are correct
            //Because the guess was not correct, the game should be over
            List<Game> associatedGame = testService.displaySingleGame(1);
            assertEquals(true, associatedGame.get(0).isIsOver());
            
            
        } catch (InvalidGuessException | GameNotFoundException ex) {
           fail("It is not expected that an exception will be thrown.");
        }
          
    }  
    
    
    @Test
    public void testEvaluateGuessedNumberInvalidBecauseOfDuplicates() {
    
        try {
            //Initialize
            Service testService = new Service(new InMemDao());
            
            //The guess provided is invalid. Guesses may not have repeated numbers
            Round testRound = new Round();
            testRound.setGameId(1);
            testRound.setGuessNum("1224");
            
            List<Round> testRounds = testService.evaluateGuessedNumber(testRound);
            fail("It is expected that an InvalidGuessExceptino will be thrown");
            
        } catch (InvalidGuessException ex) {
            //An invalid number was provided. It is expected that an InvalidGuessException
            //will be thrown
        } catch (GameNotFoundException ex) {
            fail("It is not expected that a GameNotFoundExcepion will be thrown.");
        }
        
    }
    
    @Test
    public void testEvaluateGuessedNumberInvalidBecauseTooShort() {
    
        try {
            //Initialize
            Service testService = new Service(new InMemDao());
            
            //The guess provided is invalid. Guesses must have three numbers
            Round testRound = new Round();
            testRound.setGameId(1);
            testRound.setGuessNum("124");
            
            List<Round> testRounds = testService.evaluateGuessedNumber(testRound);
            fail("It is expected that an InvalidGuessExceptino will be thrown");
            
        } catch (InvalidGuessException ex) {
            //An invalid number was provided. It is expected that an InvalidGuessException
            //will be thrown
        } catch (GameNotFoundException ex) {
            fail("It is not expected that a GameNotFoundExcepion will be thrown.");
        }
        
    }
    
    @Test
    public void testEvaluateGuessedNumberInvalidBecauseNonNumeric() {
    
        try {
            //Initialize
            Service testService = new Service(new InMemDao());
            
            //The guess provided is invalid. Guesses must have three numbers
            Round testRound = new Round();
            testRound.setGameId(1);
            testRound.setGuessNum("ABCD");
            
            List<Round> testRounds = testService.evaluateGuessedNumber(testRound);
            fail("It is expected that an InvalidGuessExceptino will be thrown");
            
        } catch (InvalidGuessException ex) {
            //An invalid number was provided. It is expected that an InvalidGuessException
            //will be thrown
        } catch (GameNotFoundException ex) {
            fail("It is not expected that a GameNotFoundExcepion will be thrown.");
        }
        
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
