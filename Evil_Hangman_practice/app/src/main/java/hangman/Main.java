package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Cory on 2/4/17.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        // java [your main class name] dictionary wordLength guesses
        String dictionaryFileName = args[0];
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);
        EvilHangmanGame game = new EvilHangmanGame();
        game.numGuesses = guesses;
        game.startGame(new File(dictionaryFileName), wordLength);
        while (game.getGameStatus() == EvilHangmanGame.GAME_STATUS.NORMAL) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Enter a guess: ");
            char userGuess = reader.next().charAt(0);
            if (Character.isLetter(userGuess)) {
                try {
                    game.makeGuess(userGuess);
                } catch (IEvilHangmanGame.GuessAlreadyMadeException e) {
                    System.out.println("You already used that letter");
                }
            } else {
                System.out.println("Invalid Input");
            }

        }
        if (game.getGameStatus() == EvilHangmanGame.GAME_STATUS.PLAYER_LOST) {
            System.out.println("You lost" + game.myDictionary.get(0));
        } else {
            System.out.println("You won!" + game.currentWord);
        }

    }
}
