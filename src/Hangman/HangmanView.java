package Hangman;

import java.io.InputStream;

public interface HangmanView {
    InputStream getIn();
    void wordPickError();
    void guessWrong(String status);
    void guessRight(String status);
    void letterOnly();
    void loss();
    void win();
}
