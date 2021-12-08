package Hangman;

import java.io.InputStream;

public interface HangmanView {
    InputStream getIn();
    void wordPickError();
    void guessWrong(String status, int mis);
    void guessRight(String status, int mis);
    void existing();
    void letterOnly();
    void loss();
    void win();
    void prompt();
    void showAnswer(String ans);
}
