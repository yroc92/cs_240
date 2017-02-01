package hangman;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        java [your main class name] dictionary wordLength guesses
        String dictionaryFileName = args[0];
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);
        EvilHangman game = new EvilHangman();

        game.setNumberOfGuesses(guesses);
        game.startGame(new File(dictionaryFileName), wordLength);
//        while (game.getGameStatus() == StudentEvilHangmanGameController.GAME_STATUS.NORMAL) {
//            game.makeGuess()
//        }
    }

}


