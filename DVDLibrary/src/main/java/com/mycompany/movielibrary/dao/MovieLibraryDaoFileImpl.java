/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movielibrary.dao;

import com.mycompany.movielibrary.dto.Movie;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chad
 */
public class MovieLibraryDaoFileImpl implements MovieLibraryDao {

    String filePath;
    
    public MovieLibraryDaoFileImpl( String filePath ){
        
        this.filePath = filePath;
    }

    @Override
    public Movie addMovie(Movie movieToAdd) throws MovieLibraryDaoException{

        List<Movie> allMovies = getAllMovies();

        int newID = 0;

        for (Movie toCheck : allMovies) {

            if (toCheck.getMovieID() > newID) {
                newID = toCheck.getMovieID();
            }
        }
        newID++;
        movieToAdd.setMovieID(newID);

        allMovies.add(movieToAdd);

        writeFile(allMovies);

        return movieToAdd;

    }

    @Override
    public List<Movie> getAllMovies() throws MovieLibraryDaoException {

        List<Movie> movieToReturn = new ArrayList<>();

        FileReader reader = null;

        try {
            reader = new FileReader(filePath);
            Scanner newScn = new Scanner(reader);

            while (newScn.hasNextLine()) {

                String line = newScn.nextLine();
                if (line.length() > 0) {
                    String[] fields = line.split("::");

                    Movie toAdd = new Movie();
                    toAdd.setMovieID(Integer.parseInt(fields[0]));
                    toAdd.setTitle(fields[1]);
                    toAdd.setReleaseDate(Integer.parseInt(fields[2]));
                    toAdd.setMpaaRating(fields[3]);
                    toAdd.setDirectorName(fields[4]);
                    toAdd.setStudio(fields[5]);
                    toAdd.setUserComment(fields[6]);

                    movieToReturn.add(toAdd);
                }
            }

        } catch (FileNotFoundException ex) {
            
            throw new MovieLibraryDaoException("ERROR: Could not write to " + filePath);

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                    throw new MovieLibraryDaoException("ERROR: Could not close reader for " + filePath);
            }
        }

        return movieToReturn;
    }

    @Override
    public Movie getMovie(String title)throws MovieLibraryDaoException {

        Movie toReturn = null;

        List<Movie> allMovies = getAllMovies();

        for (Movie toCheck : allMovies) {
            if (toCheck.getTitle().equals(title)) {
                toReturn = toCheck;
                break;
            }
        }

        return toReturn;
    }

    @Override
    public void removeMovie(int movieID) throws MovieLibraryDaoException{

        List<Movie> allMovies = getAllMovies();

        int matchIndex = -1;

        for (int i = 0; i < allMovies.size(); i++) {

            Movie toCheck = allMovies.get(i);

            if (toCheck.getMovieID() == movieID) {

                matchIndex = i;
                break;

            }

        }

        if (matchIndex != -1) {
            allMovies.remove(matchIndex);
            writeFile(allMovies);
            System.out.println("Movie successfully removed.");
        } else {

             throw new MovieLibraryDaoException("ERROR: Movie with movie ID " + movieID + " was not found.");
        }
    }

    @Override
    public void editMovie(int movieID, Movie editedMovie)throws MovieLibraryDaoException {

        List<Movie> allMovies = getAllMovies();

        int matchIndex = -1;

        for (int i = 0; i < allMovies.size(); i++) {

            Movie toCheck = allMovies.get(i);

            if (toCheck.getMovieID() == movieID) {

                matchIndex = i;
                break;

            }

        }

        if (matchIndex == -1) {
            throw new MovieLibraryDaoException("ERROR: Movie with movie ID " + movieID + " was not found.");
            
        } else {
            allMovies.remove(matchIndex);
            editedMovie.setMovieID(movieID);
            allMovies.add(editedMovie);
            writeFile(allMovies);
            System.out.println("Movie successfully edited.");

            
        }

    }

    private void writeFile(List<Movie> allMovies) throws MovieLibraryDaoException{

        FileWriter writer = null;

        try {
            writer = new FileWriter(filePath);
        } catch (IOException ex) {
            throw new MovieLibraryDaoException("ERROR: Could not write to " + filePath);
        }
        PrintWriter pw = new PrintWriter(writer);

        for (Movie toWrite : allMovies) {
            String line = convertToLine(toWrite);
            pw.println(line);
        }

        try {
            writer.close();
        } catch (IOException ex) {
            throw new MovieLibraryDaoException("ERROR: Could not close writer for " + filePath);
        }

    }

    private String convertToLine(Movie toWrite) {

        String line
                = toWrite.getMovieID() + "::"
                + toWrite.getTitle() + "::"
                + toWrite.getReleaseDate() + "::"
                + toWrite.getMpaaRating() + "::"
                + toWrite.getDirectorName() + "::"
                + toWrite.getStudio() + "::"
                + toWrite.getUserComment();

        return line;
    }
    
   

}