/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movielibrary.controller;

import com.mycompany.movielibrary.dao.MovieLibraryDao;
import com.mycompany.movielibrary.dao.MovieLibraryDaoException;
import com.mycompany.movielibrary.dao.MovieLibraryDaoFileImpl;
import com.mycompany.movielibrary.dto.Movie;
import com.mycompany.movielibrary.ui.MovieLibraryView;
import com.mycompany.movielibrary.ui.UserIO;
import com.mycompany.movielibrary.ui.UserIOConsoleImpl;
import java.util.List;

/**
 *
 * @author Chad
 */
public class MovieLibraryController {

    MovieLibraryView view;
    MovieLibraryDao dao;

    public MovieLibraryController(MovieLibraryView view, MovieLibraryDao dao) {

        this.view = view;
        this.dao = dao;

    }

    private int getUserSelection() {
        return view.showMenuGetUserSelection();
    }

    public void runProgram() {

        boolean continueProgram = true;
        try {
            while (continueProgram) {

                int userSelection = getUserSelection();

                switch (userSelection) {

                    case 1:
                        addMovie();
                        break;
                    case 2:
                        removeAMovie();
                        break;
                    case 3:
                        editAMovie();
                        break;
                    case 4:
                        listMovies();
                        break;
                    case 5:
                        getMovieInfo();
                        break;
                    case 6:
                        continueProgram = false;
                        break;
                    default:
                        view.displayInvalidSelectionBanner();
                }
            }
            view.displayExitBanner();
        } catch (MovieLibraryDaoException e) {

            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addMovie() throws MovieLibraryDaoException {

        view.displayAddMovieBanner();
        Movie newMovie = view.getNewMovieInfo();
        dao.addMovie(newMovie);
        view.displayAddSuccessBanner();
        view.displayContinueBanner();

    }

    private void listMovies() throws MovieLibraryDaoException {
        view.displayAllMoviesBanner();
        List<Movie> movieList = dao.getAllMovies();
        view.displayAllMovies(movieList);
        view.displayContinueBanner();
    }

    private void getMovieInfo() throws MovieLibraryDaoException {
        view.displayGetMovieBanner();
        String title = view.displayRequestForTitle();
        Movie moov = dao.getMovie(title);

        if (moov != null) {

            view.displayMovieDetails(moov);

        } else {
            view.displayMovieNotFoundBanner();
        }
        view.displayContinueBanner();
    }

    private void removeAMovie() throws MovieLibraryDaoException {

        try {
            view.displayRemoveMovieBanner();
            int movieID = view.displayRequestForMovieID("remove? ");
            dao.removeMovie(movieID);
            view.displayContinueBanner();
        } catch (MovieLibraryDaoException e) {

            view.displayErrorMessage(e.getMessage());
        }
    }

    private void editAMovie() throws MovieLibraryDaoException{

        try {

            view.displayEditMovieBanner();
            int movieID = view.displayRequestForMovieID("edit? ");
            Movie editedMovie = view.getNewMovieInfo();
            dao.editMovie(movieID, editedMovie);
            view.displayContinueBanner();

        } catch (MovieLibraryDaoException e) {

            view.displayErrorMessage(e.getMessage());
        }

    }

}
