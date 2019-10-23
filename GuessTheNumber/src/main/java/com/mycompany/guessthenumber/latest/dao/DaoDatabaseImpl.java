/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumber.latest.dao;

import com.mycompany.guessthenumber.latest.models.Game;
import com.mycompany.guessthenumber.latest.models.Round;
import com.mycompany.guessthenumber.latest.service.GameNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chad
 */
@Repository
@Profile({"database", "testing"})
public class DaoDatabaseImpl implements Dao {
    
    @Autowired
    JdbcTemplate template;

    @Override
    public List<Game> getAllGames() {

        //Target numbers for in progress games are suppressed with a "-"
        final String sql = "SELECT gameId, if(isOver = 0, '-',targetNum) as targetNum, isOver \n"
                + "FROM Game;";

        return template.query(sql, new GameMapper());
    }

    @Override
    public int addNewGame(String targetNum) {

        final String sql = "INSERT INTO Game(targetNum,isOver) VALUES(?,?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        template.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, targetNum);
            statement.setBoolean(2, false);
            return statement;

        }, keyHolder);

        int gameId = keyHolder.getKey().intValue();

        return gameId;

    }

    @Override
    public List<Game> getSingleGame(int gameId) throws GameNotFoundException {

        //Target numers for in progress games are suppressed with a "-"
        final String sql = "SELECT gameId, if(isOver = false, '-',targetNum) as targetNum, isOver \n"
                + "FROM Game WHERE gameId = ?;";
        
        List<Game> singleGame = template.query(sql, new GameMapper(), gameId);
        
        if(singleGame.isEmpty()){
            throw new GameNotFoundException("No game was found for the supplied id.");
        }

        return singleGame;

    }
    
    @Override
    public List<Game> getSingleGameNoSuppression(int gameId) throws GameNotFoundException{
        final String sql = "SELECT gameId, targetNum, isOver \n"
                + "FROM Game WHERE gameId = ?;";

       List<Game> singleGame = template.query(sql, new GameMapper(), gameId);
        
        if(singleGame.isEmpty()){
            throw new GameNotFoundException("No game was found for the supplied id.");
        }

        return singleGame;
    }

    @Override
    public List<Round> getRoundsForGame(int gameId) {
        final String sql = "SELECT* FROM Round WHERE gameId = ? ORDER by GuessTime ASC;";
        
        List<Round> rounds = template.query(sql, new RoundMapper(), gameId);

        return rounds;
    }


    @Override
    public void addNewRound(Round newRound) {
        
         final String sql = "INSERT INTO Round(gameId, roundId, guessNum, guessTime, exactMatchCt, partialMatchCt) VALUES(?,?,?,?,?,?);";

        template.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql);

            statement.setInt(1, newRound.getGameId());
            statement.setInt(2, newRound.getRoundId());
            statement.setString(3, newRound.getGuessNum());
            statement.setTimestamp(4, newRound.getGuessTime());
            statement.setInt(5, newRound.getExactMatchCt());
            statement.setInt(6, newRound.getPartialMatchCt());
         
            return statement;

        });

 
        
        
    }

    @Override
    public void updateGameStatus(int gameId) {
        final String sql = "UPDATE Game SET isOver = '1' WHERE gameId = ?;";
        
        template.update(sql, gameId);
        
    }

    @Override
    public void deleteDataFromDB() {
        
        final String rSQL = "DELETE FROM Round WHERE gameId >0;";
        template.update(rSQL);
        
        final String gSql = "DELETE FROM Game WHERE gameId >0;";
        template.update(gSql);
       
        
    }
    

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {

            Game gFromDB = new Game();

            gFromDB.setGameId(rs.getInt("gameId"));
            gFromDB.setTargetNum(rs.getString("targetNum"));
            gFromDB.setIsOver(rs.getBoolean("isOver"));

            return gFromDB;

        }

    }
    
    private static final class RoundMapper implements RowMapper<Round>{

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            
            Round rFromDB = new Round();
            
            rFromDB.setGameId(rs.getInt("gameId"));
            rFromDB.setRoundId(rs.getInt("roundId"));
            rFromDB.setGuessNum(rs.getString("guessNum"));
            rFromDB.setGuessTime(rs.getTimestamp("guessTime"));
            rFromDB.setExactMatchCt(rs.getInt("exactMatchCt"));
            rFromDB.setPartialMatchCt(rs.getInt("partialMatchCt"));
            
            return rFromDB;
        }
        
        
    }

}
