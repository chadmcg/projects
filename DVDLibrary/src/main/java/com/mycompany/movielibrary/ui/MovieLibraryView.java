/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movielibrary.ui;

import com.mycompany.movielibrary.dto.Movie;
import java.util.List;

/**
 *
 * @author Chad
 */
public class MovieLibraryView {

    private UserIO io;
    
    public MovieLibraryView(UserIO io) {
        
        this.io = io;
    }

    public int showMenuGetUserSelection() {

        io.print("\n----------DVD COLLECTION----------");
        io.print("Please select a menu option below: ");
        io.print("1. Add a movie to the collection ");
        io.print("2. Remove a movie from the collection ");
        io.print("3. Edit an existing movie entry ");
        io.print("4. List all movies in collection ");
        io.print("5. Display info for a movie ");
        io.print("6. Exit");
        io.print("");

        return io.readInt("Menu selection: ", 1, 6);

    }

    public Movie getNewMovieInfo() {

        String title = io.readString("Please enter the movie title: ");
        int releaseDate = io.readInt("Please enter the movie's release date: ");
        String mpaaRating = io.readString("Please enter the movie's rating: ");
        String directorName = io.readString("Please enter the movie director: ");
        String studio = io.readString("Please enter the movie studio: ");
        String userComment = io.readString("Please enter any comments you want to include: ");

        Movie currentMovie = new Movie();

        currentMovie.setTitle(title);
        currentMovie.setReleaseDate(releaseDate);
        currentMovie.setMpaaRating(mpaaRating);
        currentMovie.setDirectorName(directorName);
        currentMovie.setStudio(studio);
        currentMovie.setUserComment(userComment);

        return currentMovie;

    }

    public void displayAddMovieBanner() {

        io.print("-----Add a movie-----");

    }

    
    
       public void displayAllMoviesBanner() {
    io.print("--------All Movies--------");
}
    
    public void displayAllMovies(List<Movie> movieList){
        for (Movie currentMovie : movieList) {
        io.print(currentMovie.getMovieID() + ": "
                + currentMovie.getTitle() + " "
                + currentMovie.getReleaseDate() + " "
                + currentMovie.getMpaaRating() + " "
                + currentMovie.getDirectorName() + " "
                + currentMovie.getStudio() + " "
                + currentMovie.getUserComment());
    }
    
    }
    
    public void displayMovieDetails(Movie movieToDisplay) {

        io.print("Title:  "+ movieToDisplay.getTitle());
        io.print("Release date: " + Integer.toString(movieToDisplay.getReleaseDate()));
        io.print("Rating: " + movieToDisplay.getMpaaRating());
        io.print("Director: " + movieToDisplay.getDirectorName());
        io.print("Studio: " + movieToDisplay.getStudio());
        io.print("Comments: " + movieToDisplay.getUserComment());
        io.print("");

    }
    
    public void displayMovieNotFoundBanner() {

        io.print("A movie with that title was not found.");

    }
    
    public void displayAddSuccessBanner() {

        io.print("Movie has been added.");

    }
    
     public void displayGetMovieBanner() {
         
         io.print("--------Movie Info--------");
     }
     
     public void displayRemoveMovieBanner() {
         
         io.print("--------Remove Movie--------");
     }
     
     
     
     public void displayEditMovieBanner() {
         
         io.print("--------Edit Movie--------");
     }
     
     
     
     public void displayExitBanner() {
         
         io.print("Okay, goodbye!");
     }
     
     public void displayInvalidSelectionBanner() {
         
         io.print("The input is not a valid selection.");
     }
     
     public String displayRequestForTitle() {
         
         return io.readString("What is the name of the movie you are you looking for? ");
     }
     
     public int displayRequestForMovieID(String requestType) {
         
         return io.readInt("What is the movie ID of the movie record that you would like to " + requestType);
     }
     
     public void displayContinueBanner() {
         
         io.readString("Please hit enter to continue.");
     }
     
     public void displayErrorMessage(String errorMsg){
         
         io.print("--------ERROR--------");
         io.print(errorMsg);
         
     }

    
 

}
