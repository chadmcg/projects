/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movielibrary;

import com.mycompany.movielibrary.controller.MovieLibraryController;
import com.mycompany.movielibrary.dao.MovieLibraryDao;
import com.mycompany.movielibrary.dao.MovieLibraryDaoException;
import com.mycompany.movielibrary.dao.MovieLibraryDaoFileImpl;
import com.mycompany.movielibrary.ui.MovieLibraryView;
import com.mycompany.movielibrary.ui.UserIO;
import com.mycompany.movielibrary.ui.UserIOConsoleImpl;

/**
 *
 * @author Chad
 */
public class App {
    
    public static void main(String[] args) {
    
        
        UserIO myIO = new UserIOConsoleImpl();
        MovieLibraryView myView = new MovieLibraryView(myIO);
        MovieLibraryDao myDao = new MovieLibraryDaoFileImpl("Movies.txt");
        MovieLibraryController newController = new MovieLibraryController(myView, myDao);
      
        newController.runProgram();
        
    }
    
}
    
    
    
    
    
    
    
    
    

