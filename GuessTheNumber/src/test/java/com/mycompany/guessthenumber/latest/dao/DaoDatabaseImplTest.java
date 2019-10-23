/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumber.latest.dao;

import com.mycompany.guessthenumber.latest.TestApplicationConfiguration;
import com.mycompany.guessthenumber.latest.models.Game;
import com.mycompany.guessthenumber.latest.models.Round;
import com.mycompany.guessthenumber.latest.service.GameNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
//@Repository
@ActiveProfiles(profiles = "testing")
public class DaoDatabaseImplTest {

    @Autowired
    Dao testDao;

    public DaoDatabaseImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        testDao.deleteDataFromDB();
    }

    @After
    public void tearDown() {
        
        
    }

    
    @Test
    public void testAddNewGameAndGetAllGames() {

        try {
            //Two new games are created with pre-defined target numbers
            int gameIdOne = testDao.addNewGame("1234");
            int gameIdTwo = testDao.addNewGame("4567");
            
            //Verify that there are now two games in the DB
            List<Game> testGames = new ArrayList<>();
            testGames = testDao.getAllGames();
            
            assertEquals(2, testGames.size());
            
            //Verify that the properties of the newly-created games are correct
            int gameId = 0;
            boolean isOver = true;
            
            //-----Get the first game that was created-----
            Game testGameOne = testGames.get(0);
            
            //Get the id of the first game that was created
            int testGameOneGameId = testGameOne.getGameId();
            
            //Get the info for the first game, this time without suppression of the target num
            Game testGameOneNoSuppression = testDao.getSingleGameNoSuppression(testGameOneGameId).get(0);
            
            String testGameOneTargetNum = testGameOneNoSuppression.getTargetNum();
            Boolean testGameOneStatus = testGameOneNoSuppression.isIsOver();
            
            assertEquals(gameIdOne, testGameOneGameId);
            assertEquals("1234", testGameOneTargetNum);
            assertEquals(false, testGameOneStatus);
            
            //-----Get the second game that was created-----
            Game testGameTwo = testGames.get(1);
            
            //Get the id of the second game that was created
            int testGameTwoGameId = testGameTwo.getGameId();
            
            //Get the info for the second game, this time without suppression of the target num
            Game testGameTwoNoSuppression = testDao.getSingleGameNoSuppression(testGameTwoGameId).get(0);
            
            String testGameTwoTargetNum = testGameTwoNoSuppression.getTargetNum();
            Boolean testGameTwoStatus = testGameTwoNoSuppression.isIsOver();
            
            assertEquals(gameIdTwo, testGameTwoGameId);
            assertEquals("4567", testGameTwoTargetNum);
            assertEquals(false, testGameTwoStatus);
        } catch (GameNotFoundException ex) {
            fail("It is not expected that an exception will be thrown.");
        }

    }

    
    @Test
    public void testGetSingleGame() {
        
        try {
            //Three new games are created with pre-defined target numbers
            int gameIdOne = testDao.addNewGame("1234");
            int gameIdTwo = testDao.addNewGame("4567");
            int gameIdThree = testDao.addNewGame("8901");
            
            //Call the getSingleGame method for the third game
            //The game is not yet completed
            List<Game> singleGameTest = new ArrayList<>();
            singleGameTest = testDao.getSingleGame(gameIdThree);
            
            //Verify that the data returned from the get single game method
            //is correct for a game this is not yet finished
            assertEquals("-", singleGameTest.get(0).getTargetNum());
            assertEquals(false, singleGameTest.get(0).isIsOver());
            
            //Update the game table to assert that game three is now finished
            testDao.updateGameStatus(gameIdThree);
            
            //Call the getSingleGame method for the third game
            //The game is now completed
            List<Game> singleGameTestFinished = new ArrayList<>();
            singleGameTestFinished = testDao.getSingleGame(gameIdThree);
            
            //Verify that the data returned from the get single game method
            //is correct for a game that is finished
            assertEquals("8901", singleGameTestFinished.get(0).getTargetNum());
            assertEquals(true, singleGameTestFinished.get(0).isIsOver());
        } catch (GameNotFoundException ex) {
            fail("It is not expected that an exception will be thrown.");
        }
        
    }
    
    @Test
    public void testGetSingleGameInvalidGameId() {
        
        //Three new games are created with pre-defined target numbers
        int gameIdOne = testDao.addNewGame("2345");
        int gameIdTwo = testDao.addNewGame("3456");
        int gameIdThree = testDao.addNewGame("4567");
        
        //Call the getSingleGame method for a gameId that does not exist in the 
        //game table
        List<Game> singleGameTest = new ArrayList<>();
        try {
            singleGameTest = testDao.getSingleGame(10000);
            fail("It is expected that  a GameNotFoundException will be thrown.");
            
        } catch (GameNotFoundException ex) {
            //It is expected that a GameNotFoundException will be thrown
        }
    }


    @Test
    public void testGetSingleGameNoSuppressionAndUpdateGameStatus() {
        
        try {
            //Three new games are created with pre-defined target numbers
            int gameIdOne = testDao.addNewGame("2345");
            int gameIdTwo = testDao.addNewGame("3456");
            int gameIdThree = testDao.addNewGame("4567");
            
            //Call the getSingleGame method for the second game
            //The game is not yet completed
            List<Game> singleGameTest = new ArrayList<>();
            singleGameTest = testDao.getSingleGameNoSuppression(gameIdTwo);
            
            //Verify that the data returned from the getSingleGameNoSuppression
            //method is correct for a game this is not yet finished
            assertEquals("3456", singleGameTest.get(0).getTargetNum());
            assertEquals(false, singleGameTest.get(0).isIsOver());
            
            //Update the game table to assert that game two is now finished
            testDao.updateGameStatus(gameIdTwo);
            
            //Call the getSingleGameNoSuppression method for the second game
            //The game is now completed
            List<Game> singleGameTestFinished = new ArrayList<>();
            singleGameTestFinished = testDao.getSingleGameNoSuppression(gameIdTwo);
            
            //Verify that the data returned from the get single game method
            //is correct for a game that is finished
            assertEquals("3456", singleGameTestFinished.get(0).getTargetNum());
            assertEquals(true, singleGameTestFinished.get(0).isIsOver());
        } catch (GameNotFoundException ex) {
            fail("It is not expected that an exception will be thrown.");
        }
        
    }
    
    @Test
    public void testGetSingleGameNoSuppressionInvalidGameId() {
        
        //Three new games are created with pre-defined target numbers
        int gameIdOne = testDao.addNewGame("2345");
        int gameIdTwo = testDao.addNewGame("3456");
        int gameIdThree = testDao.addNewGame("4567");
        
        //Call the getSingleGame method for a gameId that does not exist in the 
        //game table
        List<Game> singleGameTest = new ArrayList<>();
        try {
            singleGameTest = testDao.getSingleGameNoSuppression(1000);
            fail("It is expected that a GameNotFoundException will be thrown.");
            
        } catch (GameNotFoundException ex) {
            //It is expected that a GameNotFoundException will be thrown
        }
    }
    
   
   
    @Test
    public void testAddNewRoundAndGetRoundsForGame() {
        
        //Add a new entry in the game table 
        int gameId = testDao.addNewGame("1243");
        
        //Create two new rounds for a single game. GameId corresponds to the
        //the game that was just created
        
        LocalDateTime currentLDT1 = LocalDateTime.now();
        Timestamp TS1 = Timestamp.valueOf(currentLDT1);
        
        Round testRoundOne = new Round();
        testRoundOne.setGameId(gameId);
        testRoundOne.setRoundId(1);
        testRoundOne.setGuessTime(TS1);
        testRoundOne.setGuessNum("1235");
        testRoundOne.setPartialMatchCt(1);
        testRoundOne.setExactMatchCt(2);
        
        LocalDateTime currentLDT2 = LocalDateTime.now();
        Timestamp TS2 = Timestamp.valueOf(currentLDT2);
        
        Round testRoundTwo = new Round();
        testRoundTwo.setGameId(gameId);
        testRoundTwo.setRoundId(2);
        testRoundTwo.setGuessNum("1243");
        testRoundTwo.setGuessTime(TS2);
        testRoundTwo.setPartialMatchCt(0);
        testRoundTwo.setExactMatchCt(4);
        
        //Add newly created rounds to the DB
        testDao.addNewRound(testRoundOne);
        testDao.addNewRound(testRoundTwo);
        
        //Retrieve the rounds from the DB for game 1
        List<Round> allRounds = new ArrayList<>();
        allRounds = testDao.getRoundsForGame(gameId);
        
        //Verify that the attributes of the first round are correct
        assertEquals(gameId, allRounds.get(0).getGameId());
        assertEquals(1, allRounds.get(0).getRoundId());
        assertEquals("1235", allRounds.get(0).getGuessNum());
        assertEquals(((TS1.getTime()/100000)*100000), (allRounds.get(0).getGuessTime().getTime()/100000)*100000);
        assertEquals(1, allRounds.get(0).getPartialMatchCt());
        assertEquals(2, allRounds.get(0).getExactMatchCt());
        
        //Verify that the attributes of the second round are correct
        assertEquals(gameId, allRounds.get(1).getGameId());
        assertEquals(2, allRounds.get(1).getRoundId());
        assertEquals("1243", allRounds.get(1).getGuessNum());
//        assertEquals(((TS2.getTime()/10000)*10000), (allRounds.get(0).getGuessTime().getTime()/10000)*10000);
        assertEquals(0, allRounds.get(1).getPartialMatchCt());
        assertEquals(4, allRounds.get(1).getExactMatchCt());
        
    }


}
