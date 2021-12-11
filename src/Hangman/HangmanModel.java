package Hangman;

import java.io.File;

/**
 * a model for hangman game select a random word from dictionary file and taking guesses
 */
public interface HangmanModel {

    /**
     * take the word
     *
     * @param word to guess
     * @return 1 on success, 0 on failure
     */
    int takeWord(String word);

    /**
     * @return the number of mistakes made
     */
    int getMis();

    /**
     * @return is the game over or not
     */
    boolean isOver();

    /**
     * @return the string indicating the game status
     */
    String getStatus();

    /**
     * take a character for guess
     *
     * @param c the character
     * @return 1 if right, 0 if wrong, -1 if the char has been shown
     */
    int guess(char c);

    /**
     * take a string to guess the whole word
     *
     * @param s the string
     * @return 1 if right, 0 if wrong
     */
    int guess(String s);

    /**
     * return the word
     *
     * @return the word as a string
     */
    String getWord();

    /**
     * return the win/loss when game is over
     * called only when over
     *
     * @return true if won, false if lost
     * @throws IllegalStateException when not over
     */
    boolean isWin() throws IllegalStateException;
}
