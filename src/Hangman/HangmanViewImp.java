package Hangman;

import java.io.InputStream;
import java.io.PrintStream;

public class HangmanViewImp implements HangmanView {
    private final InputStream in;
    private final PrintStream out;
    public HangmanViewImp(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public InputStream getIn() {
        return in;
    }

    @Override
    public void wordPickError() {
        this.out.println("dictionary file error\n");
    }

    @Override
    public void guessWrong(String status) {
        this.out.println("wrong\n" + status + "\n");
    }

    @Override
    public void guessRight(String status) {
        this.out.println("right\n" + status + "\n");
    }

    @Override
    public void letterOnly() {
        this.out.println("letter only\n");
    }

    @Override
    public void loss() {
        this.out.println("loss.\n");

    }

    @Override
    public void win() {
        this.out.println("win.\n");
    }

}
