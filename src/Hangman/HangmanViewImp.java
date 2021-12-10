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

    /**
     * print the current hangman image
     *
     * @param mis the number of mistakes so far
     */
    private void draw(int mis) {
        String s;
        switch (mis) {
            case 1 -> s = """
                    
                    |
                    |
                    |
                    |
                    |
                    |
                    |
                    ----------""";
            case 2 -> s = """
                     ------
                    |     |
                    |
                    |
                    |
                    |
                    |
                    |
                    ----------""";
            case 3 -> s = """
                     ------
                    |     |
                    |     O
                    |
                    |
                    |
                    |
                    |
                    ----------""";
            case 4 -> s = """
                     ------
                    |     |
                    |     O
                    |     +
                    |     |
                    |
                    |
                    |
                    ----------""";
            case 5 -> s = """
                     ------
                    |     |
                    |     O
                    |   --+
                    |     |
                    |
                    |
                    |
                    ----------""";
            case 6 -> s = """
                     ------
                    |     |
                    |     O
                    |   --+--
                    |     |
                    |
                    |
                    |
                    ----------""";
            case 7 -> s = """
                     ------
                    |     |
                    |     O
                    |   --+--
                    |     |
                    |    /
                    |   /
                    |
                    ----------""";
            case 8 -> s = """
                    ------
                    |     |
                    |     O
                    |   --+--
                    |     |
                    |    / \\
                    |   /   \\
                    |
                    ----------""";
            default -> s = """









                    ----------""";
        }
        this.out.println(s);
    }
    @Override
    public InputStream getIn() {
        return in;
    }

    @Override
    public void wordPickError() {
        this.out.println("dictionary file error");
    }

    @Override
    public void guessWrong(String status, int mis) {
        this.out.println("wrong\nhangman status:");
        this.draw(mis);
        this.out.println("word: " + status);
    }

    @Override
    public void guessRight(String status, int mis) {
        this.out.println("right\nhangman status:");
        this.draw(mis);
        this.out.println("word: " + status);
    }

    @Override
    public void existing() {
        this.out.println("existing letter");
    }

    @Override
    public void letterOnly() {
        this.out.println("letter only");
    }

    @Override
    public void loss() {
        this.out.println("loss.");
    }

    @Override
    public void win() {
        this.out.println("win.");
    }

    @Override
    public void prompt() {
        this.out.println("input your guess: ");
    }

    @Override
    public void showAnswer(String ans) {
        this.out.println("The word is " + "\"" + ans + "\"");
    }

}
