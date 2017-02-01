package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
//        java [your main class name] dictionary wordLength guesses
        String dictionaryFileName = args[0];
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);
        EvilHangman game = new EvilHangman();

        game.setNumberOfGuesses(guesses);
        game.startGame(new File(dictionaryFileName), wordLength);
        while (game.getGameStatus() == StudentEvilHangmanGameController.GAME_STATUS.NORMAL) {
            System.out.println("Guesses left: " + game.getNumberOfGuessesLeft());
            System.out.println("Used letters: ");
            for (Character c : game.getUsedLetters()) {
                System.out.print(c + " ");
            }
            System.out.println("Current word: " + game.getCurrentWord());

            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("\nEnter a guess: ");
            char userGuess = reader.next().charAt(0); // Scans the next token of the input as an int.
            if (Character.isLetter(userGuess)) {
                try {
                    game.makeGuess(userGuess);
                } catch (IEvilHangmanGame.GuessAlreadyMadeException e) {
//                    e.printStackTrace();
                    System.out.println("You already used that letter");
                }
            } else {
                System.out.println("Invalid Input");
            }
        }
        if (game.getGameStatus() == StudentEvilHangmanGameController.GAME_STATUS.PLAYER_LOST) {
            System.out.println("You lost! " +game.myDictionary.get(0));
        } else {
            System.out.println("You won!" + game.getCurrentWord());
        }
    }

}


