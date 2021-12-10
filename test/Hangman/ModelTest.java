package Hangman;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ModelTest {
    private HangmanModel m;

    /**
     * pass one word "WORD" upper case in a temporary dictionary to the model
     * since this game ignores the letter case, such robustness will be tested given a lower case input
     *
     */
    @Before
    public void setUp() throws IOException {
        this.m = new HangmanModelImp();
        this.m.takeWord("WORD");
    }

    @Test
    public void testGetWord() {
        assertEquals("word", this.m.getWord());
    }


    @Test
    public void testPickWord() {
        assertEquals(1, this.m.takeWord("word"));
        assertEquals("word", this.m.getWord());
    }

    @Test
    public void testGetMis() {
        //string error
        this.m.guess("string");
        assertEquals(1, this.m.getMis());
        //char error
        this.m.guess('a');
        assertEquals(2, this.m.getMis());
        //char correct
        this.m.guess('o');
        assertEquals(2, this.m.getMis());
        //string correct
        this.m.guess("word");
        assertEquals(2, m.getMis());
    }
    @Test
    public void testIsOverLoss() {
        for (int i = 0; i < 7; i++) {
            this.m.guess('a');
            assertFalse(this.m.isOver());
        }
        this.m.guess('a');
        assertTrue(this.m.isOver());
    }
    @Test
    public void testGetStatus() {
        //initial status
        assertEquals("____", m.getStatus());
        //shows something
        this.m.guess('r');
        assertEquals("__r_", m.getStatus());
        //shows nothing wrong
        this.m.guess('a');
        assertEquals("__r_", m.getStatus());
    }

    @Test
    public void testGuess() {
        //wrong guess
        assertEquals(0, this.m.guess('a'));
        //right guess
        assertEquals(1, this.m.guess('w'));
        //shown letter
        assertEquals(-1, this.m.guess('w'));
        //wrong string guess
        assertEquals(0, this.m.guess("dowrd"));
        //right string guess
        assertEquals(1, this.m.guess("word"));
    }
}