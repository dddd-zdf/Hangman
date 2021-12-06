package Hangman;

/**
 * a model for hangman game
 * select a random word from dictionary file and taking guesses
 */
public interface HangmanModel {
    /**
     * pick a word by read dictionary given the path
     * @param s the path
     * @return 1 on success, 0 if failed
     */
    int pickWord(String s);

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
     * @param c the character
     * @return 1 if right, 0 if wrong
     */
    int guess(char c);

    /**
     * take a string to guess the whole word
     * @param s the string
     * @return 1 if right, 0 if wrong
     */
    int guess(String s);
}
