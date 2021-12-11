package Hangman;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static org.junit.Assert.*;


/**
 * for the record, since unit test is not good for randomness, this is not an exhaustive test
 */
public class ControllerTest {
    private HangmanController c;
    private HangmanModel m;
    private HangmanView v;
    private StringBuffer viewIn;
    private StringBuilder viewLog;
    private File testFile;

    static class MockView implements HangmanView {
        private final StringBuilder log;
        private final InputStream inputStream;

        public MockView(StringBuffer in, StringBuilder log) {
            this.log = log;
            this.inputStream = new ByteArrayInputStream(in.toString().getBytes());
        }

        @Override
        public InputStream getIn() {
            return this.inputStream;
        }

        @Override
        public void wordPickError() {
            this.log.append("wordPickError call\n");
        }

        @Override
        public void guessWrong(String status, int mis) {
            this.log.append("guessWrong call\n");
        }

        @Override
        public void guessRight(String status, int mis) {
            this.log.append("guessRight call\n");
        }

        @Override
        public void existing() {
            this.log.append("existing call\n");
        }

        @Override
        public void letterOnly() {
            this.log.append("letterOnly call\n");
        }

        @Override
        public void loss() {
            this.log.append("loss call\n");
        }

        @Override
        public void win() {
            this.log.append("win call\n");
        }

        @Override
        public void prompt() {
            this.log.append("prompt call\n");
        }

        @Override
        public void showAnswer(String ans) {
            this.log.append("showAnswer call\n");
        }
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * creates a test dictionary with one word "WORD", a mock model and a mock view to record input and output
     *
     * @throws IOException when file IO fails
     */
    @Before
    public void setUp() throws IOException {
        this.viewIn = new StringBuffer();
        this.viewLog = new StringBuilder();
        this.m = new HangmanModelImp();
        this.testFile = this.tempFolder.newFile("test.txt");
        FileWriter writer = new FileWriter(this.testFile);
        writer.write("WORD");
        writer.close();
    }

    /**
     * testFile is a real dictionary while dummyFile is an invalid one.
     *
     * @throws IOException when file IO fails
     */
    @Test
    public void testPickWord() throws IOException {
        //avoid @Before setup
        this.v = new MockView(this.viewIn, this.viewLog);
        this.c = new HangmanControllerImp(this.m, this.v);
        File testFile = this.tempFolder.newFile("pickWordTest.txt");
        File dummyFile = new File("./abc.txt");
        FileWriter writer = new FileWriter(testFile);
        writer.write("WORD");
        writer.close();
        assertEquals("word", this.c.pickWord(testFile));
        assertNull(this.c.pickWord(dummyFile));
    }

    @Test
    public void testPlayGameWinByWord() {
        this.viewIn.append("""
                a
                b
                word""");
        this.v = new MockView(this.viewIn, this.viewLog);
        this.c = new HangmanControllerImp(m, v);
        this.c.pickWord(this.testFile);
        this.c.playGame();
        assertEquals("""
                prompt call
                guessWrong call
                prompt call
                guessWrong call
                prompt call
                win call
                """, this.viewLog.toString());
    }

    @Test
    public void testPlayGameWin1By1WithNonLetter() {
        this.viewIn.append("""
                word
                123
                o
                :word
                w
                o
                m
                r
                d
                """);
        this.v = new MockView(this.viewIn, this.viewLog);
        this.c = new HangmanControllerImp(this.m, this.v);
        this.c.pickWord(this.testFile);
        this.c.playGame();
        assertEquals("""
                prompt call
                win call
                """, this.viewLog.toString());
    }

    @Test
    public void testPlayGameLoss() {
        this.viewIn.append("""
                a
                a
                b
                b
                kk
                kk
                kk
                kk
                """);
        this.v = new MockView(this.viewIn, this.viewLog);
        this.c = new HangmanControllerImp(this.m, this.v);
        this.c.pickWord(this.testFile);
        this.c.playGame();
        assertEquals("""
                prompt call
                guessWrong call
                prompt call
                guessWrong call
                prompt call
                guessWrong call
                prompt call
                guessWrong call
                prompt call
                guessWrong call
                prompt call
                guessWrong call
                prompt call
                guessWrong call
                prompt call
                guessWrong call
                loss call
                showAnswer call
                """, this.viewLog.toString());
    }
}
