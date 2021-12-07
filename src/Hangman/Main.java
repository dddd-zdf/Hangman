package Hangman;

public class Main {
    public static void main(String[] args) {
        HangmanModel m = new HangmanModelImp();
        HangmanView v = new HangmanViewImp(System.in, System.out);
        HangmanController controller = new HangmanControllerImp(m, v);
        controller.playGame();
    }
}
