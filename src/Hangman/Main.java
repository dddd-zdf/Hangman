package Hangman;

import java.io.File;

public class Main {
  public static void main(String[] args) {
    File dictionary = new File("./dictionary.txt");
    HangmanModel m = new HangmanModelImp();
    HangmanView v = new HangmanViewImp(System.in, System.out);
    HangmanController controller = new HangmanControllerImp(m, v);

    controller.pickWord(dictionary);
    controller.playGame();
  }
}
