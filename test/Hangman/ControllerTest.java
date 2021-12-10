package Hangman;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static org.junit.Assert.*;


/** for the record, since unit test is not good for randomness, this is not an exhaustive test */
public class ControllerTest {
    private HangmanController c;
    private HangmanModel m;
    private HangmanView v;
    private StringBuffer viewIn;
    private StringBuilder viewLog;
    private StringBuilder modelLog;
    private File testFile;
    static class MockModel implements HangmanModel {
        private final StringBuilder log;
        public MockModel(StringBuilder log) {
            this.log = log;
        }


        @Override
        public int takeWord(String word) {
            this.log.append("takeWord call\n");
            return 0;
        }

        @Override
        public int getMis() {
            this.log.append("getMis call\n");
            return 0;
        }

        @Override
        public boolean isOver() {
            this.log.append("isOver call\n");
            return false;
        }

        @Override
        public String getStatus() {
            this.log.append("getStatus call\n");
            return "status";
        }

        @Override
        public int guess(char c) {
            this.log.append("guess call\n");
            return 0;
        }

        @Override
        public int guess(String s) {
            this.log.append("guess string call\n");
            return 0;
        }

        @Override
        public String getWord() {
            this.log.append("getWord call\n");
            return "dummy";
        }
    }
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
        this.modelLog = new StringBuilder();

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
        this.m = new MockModel(this.modelLog);
        this.v = new MockView(this.viewIn, this.modelLog);
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
                dummy""");
        this.v = new MockView(this.viewIn, this.viewLog);
        this.m = new MockModel(this.modelLog);
        this.c = new HangmanControllerImp(m, v);
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
                win call
                """, this.viewLog.toString());
        assertEquals("""
                takeWord call
                guess call
                getStatus call
                getMis call
                isOver call
                guess call
                getStatus call
                getMis call
                isOver call
                guess string call
                getStatus call
                getMis call
                isOver call
                getMis call
                """, this.modelLog.toString());
    }
    @Test
    public void testPlayGameWin1By1WithNonLetter() {
        this.viewIn.append("""
                word
                123
                o
                :dummy
                d
                k
                m
                u
                y
                """);
        this.v = new MockView(this.viewIn, this.viewLog);
        this.m = new MockModel(this.modelLog);
        this.c = new HangmanControllerImp(this.m, this.v);
        this.c.pickWord(this.testFile);
        this.c.playGame();
        assertEquals("""
                prompt call
                guessWrong call
                prompt call
                letterOnly call
                prompt call
                guessWrong call
                prompt call
                letterOnly call
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
                win call
                """, this.viewLog.toString());
        assertEquals("""
                takeWord call
                guess string call
                getStatus call
                getMis call
                isOver call
                isOver call
                guess call
                getStatus call
                getMis call
                isOver call
                isOver call
                guess call
                getStatus call
                getMis call
                isOver call
                guess call
                getStatus call
                getMis call
                isOver call
                guess call
                getStatus call
                getMis call
                isOver call
                guess call
                getStatus call
                getMis call
                isOver call
                guess call
                getStatus call
                getMis call
                isOver call
                getMis call
                """, this.modelLog.toString());
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
        this.m = new MockModel(this.modelLog);
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
                prompt call
                win call
                """, this.viewLog.toString());
        assertEquals("""
                takeWord call
                guess call
                getStatus call
                getMis call
                isOver call
                guess call
                getStatus call
                getMis call
                isOver call
                guess call
                getStatus call
                getMis call
                isOver call
                guess call
                getStatus call
                getMis call
                isOver call
                guess string call
                getStatus call
                getMis call
                isOver call
                guess string call
                getStatus call
                getMis call
                isOver call
                guess string call
                getStatus call
                getMis call
                isOver call
                guess string call
                getStatus call
                getMis call
                isOver call
                getMis call
                """, this.modelLog.toString());
    }
}
