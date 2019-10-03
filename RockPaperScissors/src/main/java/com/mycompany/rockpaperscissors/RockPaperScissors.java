/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rockpaperscissors;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Chad
 */
public class RockPaperScissors {

    public static void main(String[] args) {

        Scanner newScn = new Scanner(System.in);
        Random newRandomizer = new Random();

        //After all rounds, user will be be asked whether they want to play again.
        //An input response other than '1' (continue) will terminate the program
        int continuePlaying = 2;

        do {
            int numOfRounds = -1;

            int tieCt = 0;
            int winCt = 0;
            int lossCt = 0;

            //User must elect to play between 1 and 10 rounds
            //A response outside this range will reprompt the user
            while (numOfRounds < 1 || numOfRounds > 10) {

                System.out.print("\nHow many rounds would you like to play? (No more than 10, please!) ");

                numOfRounds = newScn.nextInt();

                if (numOfRounds < 1 || numOfRounds > 10) {

                    System.out.println("Hmm, that number doesn't seem to be a valid selection.");
                }
            }

            //The r/p/s game will repeat for the number of rounds that the user selected
            for (int i = 1; i <= numOfRounds; i++) {

                //User must provide a numerical input corresponding to their selection of rock, paper or scissors
                //Rock = 1; paper = 2; scissors = 3
                System.out.println("---ROUND " + i + "---");
                System.out.println("Your options... ");
                System.out.println("1. Rock");
                System.out.println("2. Paper");
                System.out.println("3. Scissors");
                System.out.print("Note your selection here: ");

                int userSelection = newScn.nextInt();

                //The selection that the user made is stated
                switch (userSelection) {

                    case 1:
                        System.out.println("\nYou chose rock.");
                        break;
                    case 2:
                        System.out.println("\nYou chose paper!");
                        break;
                    case 3:
                        System.out.println("\nYou chose scissors!");
                        break;
                    default:
                        System.out.println("\nAck! Something went wrong with the user selection!");

                }

                //A random # between 1 and 3--corresponding to the computer's selectino of r/p/s is generated
                int compSelection = newRandomizer.nextInt(3) + 1;

                //The 'selection' that the computer made is stated
                switch (compSelection) {

                    case 1:
                        System.out.println("The computer chose rock!");
                        break;
                    case 2:
                        System.out.println("The computer chose paper!");
                        break;
                    case 3:
                        System.out.println("The computer chose scissors!");
                        break;
                    default:
                        System.out.println("Ack! Something went wrong with the computer selection!");

                }

                //Outcome of match is stated. 
                //Rock (1) beats scissors (3); scissors (3) beats paper (2); paper (2) beats rock (1)
                //Win/loss/tie counters are updated.
                if (userSelection == compSelection) {
                    System.out.println("It's a TIE!\n");
                    tieCt++;
                } else if (userSelection == 1 && compSelection == 2) {
                    System.out.println("Paper beats rock. You LOSE!\n");
                    lossCt++;
                } else if (userSelection == 1 && compSelection == 3) {
                    System.out.println("Rock beats scissors. You WIN!\n");
                    winCt++;
                } else if (userSelection == 2 && compSelection == 1) {
                    System.out.println("Paper beats rock. You WIN!\n");
                    winCt++;
                } else if (userSelection == 2 && compSelection == 3) {
                    System.out.println("Scissors beats paper. You LOSE!\n");
                    lossCt++;
                } else if (userSelection == 3 && compSelection == 1) {
                    System.out.println("Rock beats scissors. You LOSE!\n");
                    lossCt++;
                } else if (userSelection == 3 && compSelection == 2) {
                    System.out.println("Scissor beats paper. You WIN!\n");
                    winCt++;
                } else {
                    System.out.println("Ack! Something went wrong!\n");
                }
            }

            //Declare overall winner. Display summary statistics for all rounds played in game.
            System.out.println("After " + numOfRounds + " rounds of competition...");
            if (winCt > lossCt) {
                System.out.println("YOU have been declared the ultimate rock paper scissors champion! Congratulations!\n");
            } else if (winCt < lossCt) {
                System.out.println("THE COMPUTER has been declared the ultimate rock paper scissors champion! Better luck next time!\n");
            } else {
                System.out.println("No winner can be delared! It's a tie!\n");
            }

            System.out.println("Rounds: " + numOfRounds);
            System.out.println("Wins: " + winCt);
            System.out.println("Losses: " + lossCt);
            System.out.println("Ties: " + tieCt + "\n");

            //User is asked if they would like to play again.
            System.out.print("Would you like to play again? Press '1' to play again or any other number to exit.");

            continuePlaying = newScn.nextInt();

            //A user input of '1' will initiate a new game; any other numerical input will terminate the program.
            if (continuePlaying != 1) {
                System.out.println("Ok! Thanks for playing!");
            }

        } while (continuePlaying == 1);

    }
}
