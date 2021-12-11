package Hangman;

import java.io.File;

public class Main {
  public static void main(String[] args) {
    //hardcoded path to dictionary
    File dictionary = new File("./res/dictionary.txt");
    HangmanModel m = new HangmanModelImp();
    //console as view
    HangmanView v = new HangmanViewImp(System.in, System.out);
    HangmanController controller = new HangmanControllerImp(m, v);

    controller.pickWord(dictionary);
    controller.playGame();
  }
}
