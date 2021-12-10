package Hangman;

import java.io.File;

public interface HangmanController {
  /** initialize a hangman game with given model and view */
  void playGame();
  /**
   * pick a word by read dictionary given the path
   *
   * @param dictionary the dictionary file
   * @return the word as a string
   */
  String pickWord(File dictionary);
}
