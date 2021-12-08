package Hangman;

import java.util.Scanner;
import java.util.regex.Pattern;

public class HangmanControllerImp implements HangmanController {
    //hard-coded, put on top for modification
    private final String path = "./dictionary.txt";
    private final HangmanModel model;
    private final Scanner in;
    private final HangmanView view;

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
        this.view.prompt();
        while (this.in.hasNext()) {
            buffer = this.in.next();
            //all are letters
            if (isLetter(buffer)) {
                //guess the whole word
                if (buffer.length() != 1) {
                    //correct
                    if (this.model.guess(buffer) == 1) {
                        break;
                        //wrong
                    } else {
                        this.view.guessWrong(this.model.getStatus(), this.model.getMis());
                    }
                    // 1 char
                } else {
                    switch (this.model.guess(buffer.toLowerCase().charAt(0))) {
                        case -1 -> this.view.existing();
                        case 0 -> this.view.guessWrong(this.model.getStatus(), this.model.getMis());
                        case 1 -> this.view.guessRight(this.model.getStatus(), this.model.getMis());
                    }
                }
                //not letter
            } else {this.view.letterOnly();}
            //check gameOver
            if (this.model.isOver()) {break;} else {this.view.prompt();}
        }
        if (this.model.getMis() == 8) {
            this.view.loss();
            this.view.showAnswer(model.getWord());
        }
        else {
            this.view.win();
        }

    }
}
