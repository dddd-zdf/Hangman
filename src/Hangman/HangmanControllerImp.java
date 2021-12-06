package Hangman;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HangmanControllerImp implements HangmanController {
    private final String path = "./dictionary.txt";
    private final Readable input;
    private final Appendable gameLog;

    private static boolean isLetter(String s) {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        if (s == null) {
            return false;
        }
        return p.matcher(s).matches();
    }

    public HangmanControllerImp(Readable input, Appendable gameLog) {
        if (input == null || gameLog == null) {throw new IllegalArgumentException("null IO");}
        this.input = input;
        this.gameLog = gameLog;
    }
    @Override
    public void playGame(HangmanModel m) {
        try {
            //pick word
            if (m.pickWord(path) == 0) {
                this.gameLog.append("invalid dictionary file\n");
                System.exit(0);
            }
            Scanner sc = new Scanner(this.input);
            String buffer;
            while (sc.hasNext()) {
                buffer = sc.next();
                if (isLetter(buffer)) {
                    if (buffer.length() != 1) {
                        if (m.guess(buffer) == 1) {
                            break;
                        } else {
                            this.gameLog.append("wrong\n").append(m.getStatus()).append("\n");
                        }
                        // 1 char
                    } else {
                        if (m.guess(buffer.toLowerCase().charAt(0)) == 1) {
                            this.gameLog.append("correct\n").append(m.getStatus()).append("\n");
                        } else {
                            this.gameLog.append("wrong\n").append(m.getStatus()).append("\n");
                        }
                    }
                } else {this.gameLog.append("letter only\n");}
                if (m.isOver()) {break;}
            }
            if (m.getMis() == 8) {this.gameLog.append("lost.\n");}
            else {this.gameLog.append("win.\n");}

        }
        catch (IOException e) {throw new IllegalStateException("IO error");}
    }
}
