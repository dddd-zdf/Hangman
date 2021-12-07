package Hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class HangmanModelImp implements HangmanModel {
    private int mis;
    private String word;
    private char[] status;

    /**
     * check if numeric
     *
     * @param s s to check
     * @return true if yes, vice versa
     */
    private static boolean isWord(String s) {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        if (s == null) {
            return false;
        }
        return p.matcher(s).matches();
    }
    public HangmanModelImp() {
        this.mis = 0;
    }

    @Override
    public int pickWord(String s) {
        ArrayList<String> dict = new ArrayList<>();
        File dictionary = new File(s);
        try {
            Scanner sc = new Scanner(dictionary);
            while (sc.hasNextLine()) {
                String buffer = sc.next();
                if (isWord(buffer)) {
                    dict.add(buffer.toLowerCase());
                }
            }
            Random random = new Random();
            this.word = dict.get(random.nextInt(dict.size()));
            this.status = new char[word.length()];
            for (int i = 0; i < word.length(); i++) {
                this.status[i] = '_';
            }
            return 1;
        }
        catch (FileNotFoundException e) {
            return 0;
        }
    }

    @Override
    public int getMis() {
        return this.mis;
    }

    @Override
    public boolean isOver() {
        if (this.mis == 8) {return true;}
        for (char c: this.status) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getStatus() {
        String output;
        return Arrays.toString(this.status);
    }


    @Override
    public int guess(char c) {
        int mark = 0;
        for (int i = 0; i < this.word.length(); i++) {

            if (this.word.charAt(i) == c) {
                this.status[i] = c;
                mark = 1;
            }
        }
        if (mark == 0) {
            this.mis++;
            return 0;
        }
        return 1;
    }
    @Override
    public int guess(String s) {
        if (this.word.equals(s)) {
            this.status = this.word.toCharArray();
            return 1;
        }
        else {
            this.mis++;
            return 0;
        }
    }
}
