package Hangman;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.*;

/**
 * tests may fail on Windows since it's a different line separator.
 */
public class ViewTest {
    private HangmanView v;

    private final ByteArrayOutputStream log = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(log));
        this.v = new HangmanViewImp(System.in, System.out);
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    public void getIn() {
        InputStream in = new ByteArrayInputStream("nothing".getBytes());
        this.v = new HangmanViewImp(in, System.out);
        assertEquals(in, this.v.getIn());
    }

    @Test
    public void wordPickError() {
        this.v.wordPickError();
        assertEquals("dictionary file error\n", this.log.toString());
    }

    @Test
    public void guessWrong() {
        this.v.guessWrong("abc", 1);
        assertEquals(
                """
                        wrong
                        hangman status:

                        |
                        |
                        |
                        |
                        |
                        |
                        |
                        ----------
                        word: abc
                        """,
                this.log.toString());
    }

    @Test
    public void guessRight() {
        this.v.guessRight("abc", 1);
        assertEquals(
                """
                        right
                        hangman status:

                        |
                        |
                        |
                        |
                        |
                        |
                        |
                        ----------
                        word: abc
                        """,
                this.log.toString());
    }

    @Test
    public void existing() {
        this.v.existing();
        assertEquals("existing letter\n", this.log.toString());
    }

    @Test
    public void letterOnly() {
        this.v.letterOnly();
        assertEquals("letter only\n", this.log.toString());
    }

    @Test
    public void loss() {
        this.v.loss();
        assertEquals("loss.\n", this.log.toString());
    }

    @Test
    public void win() {
        this.v.win();
        assertEquals("win.\n", this.log.toString());
    }

    @Test
    public void prompt() {
        this.v.prompt();
        assertEquals("input your guess: \n", this.log.toString());
    }

    @Test
    public void showAnswer() {
        this.v.showAnswer("abc");
        assertEquals("The word is \"abc\"\n", this.log.toString());
    }
}