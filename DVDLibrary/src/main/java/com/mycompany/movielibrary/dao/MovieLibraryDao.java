/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movielibrary.dao;

import com.mycompany.movielibrary.dto.Movie;
import java.util.List;

/**
 *
 * @author Chad
 */
public interface MovieLibraryDao {
    
    Movie addMovie(Movie movieToAdd) throws MovieLibraryDaoException;
    
    List<Movie> getAllMovies() throws MovieLibraryDaoException;
    
    Movie getMovie(String title)throws MovieLibraryDaoException;
    
    void removeMovie(int movieID)throws MovieLibraryDaoException;
    
    void editMovie(int movieID, Movie editedMovie)throws MovieLibraryDaoException;
    
    

    
}
