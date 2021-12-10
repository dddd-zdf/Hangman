package Hangman;

import java.io.InputStream;

public interface HangmanView {
  /** @return input stream from the view for the controller */
  InputStream getIn();

  /** show the word pick error */
  void wordPickError();

  /**
   * show the wrong guess
   *
   * @param status the current guess status of the word
   * @param mis number of mistakes made so far
   */
  void guessWrong(String status, int mis);

  /**
   * show the right guess
   *
   * @param status status the current guess status of the word
   * @param mis number of mistakes made so far
   */
  void guessRight(String status, int mis);

  /** show the letter has been shown in the word */
  void existing();

  /** show input error where only letter will be taken */
  void letterOnly();

  /** show game loss */
  void loss();

  /** show game win */
  void win();

  /** prompt for a new input */
  void prompt();

  /**
   * show the correct answer when it's a loss
   *
   * @param ans the word
   */
  void showAnswer(String ans);
}
