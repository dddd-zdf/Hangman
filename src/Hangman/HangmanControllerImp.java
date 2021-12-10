package Hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HangmanControllerImp implements HangmanController {
    //hard-coded, put on top for modification
    private final HangmanModel model;
    private final Scanner in;
    private final HangmanView view;
    /**
     * check if numeric
     *
     * @param s s to check
     * @return true if yes, vice versa
     */
    private static boolean isLetter(String s) {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        if (s == null) {return false;}
        return p.matcher(s).matches();
    }

    public HangmanControllerImp(HangmanModel m, HangmanView v) {
        if (m == null || v == null) {throw new IllegalArgumentException("null IO");}
        this.model = m;
        this.in = new Scanner(v.getIn());
        this.view = v;
    }

    @Override
    public String pickWord(File dictionary) {
        ArrayList<String> dict = new ArrayList<>();
        try {
            Scanner sc = new Scanner(dictionary);
            while (sc.hasNextLine()) {
                String buffer = sc.next();
                if (isLetter(buffer)) {
                    dict.add(buffer.toLowerCase());
                }
            }
            Random random = new Random();
            String word = dict.get(random.nextInt(dict.size()));
            this.model.takeWord(word);
            return word;
        }
        catch (FileNotFoundException e) {
            this.view.wordPickError();
            return null;
        }
    }
    @Override
    public void playGame() {
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
                    switch (this.model.guess(buffer.charAt(0))) {
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
