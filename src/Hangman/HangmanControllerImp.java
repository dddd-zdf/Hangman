package Hangman;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HangmanControllerImp implements HangmanController {
    private HangmanModel model;
    private Scanner in;
    private HangmanView view;
    private final String path = "./dictionary.txt";

    private static boolean isLetter(String s) {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        if (s == null) {
            return false;
        }
        return p.matcher(s).matches();
    }

    public HangmanControllerImp(HangmanModel m, HangmanView v) {
        if (m == null || v == null) {throw new IllegalArgumentException("null IO");}
        this.model = m;
        this.in = new Scanner(v.getIn());
        this.view = v;
    }
    @Override
    public void playGame() {
        //pick word
        if (this.model.pickWord(path) == 0) {
            this.view.wordPickError();
            System.exit(0);
        }
        String buffer;
        while (this.in.hasNext()) {
            buffer = this.in.next();
            if (isLetter(buffer)) {
                if (buffer.length() != 1) {
                    if (this.model.guess(buffer) == 1) {
                        break;
                    } else {
                        this.view.guessWrong(model.getStatus());
                        //this.gameLog.append("wrong\n").append(m.getStatus()).append("\n");
                    }
                    // 1 char
                } else {
                    if (this.model.guess(buffer.toLowerCase().charAt(0)) == 1) {
                        this.view.guessRight(model.getStatus());
                        //this.gameLog.append("correct\n").append(m.getStatus()).append("\n");
                    } else {
                        this.view.guessWrong(model.getStatus());
                        //this.gameLog.append("wrong\n").append(m.getStatus()).append("\n");
                    }
                }
            } else {
                this.view.letterOnly();
                //this.gameLog.append("letter only\n");
            }
            if (this.model.isOver()) {break;}
        }
        if (this.model.getMis() == 8) {
            this.view.loss();
            //this.gameLog.append("lost.\n");
        }
        else {
            this.view.win();
            //this.gameLog.append("win.\n");
        }

    }
}
