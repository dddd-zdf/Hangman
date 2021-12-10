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
  private static boolean isLetter(String s) {
    Pattern p = Pattern.compile("[a-zA-Z]+");
    if (s == null) {return false;}
    return p.matcher(s).matches();
  }

  public HangmanModelImp() {
    this.mis = 0;
  }

  @Override
  public int takeWord(String word) {
    if (word.equals("null")) {return 0;}
    word = word.toLowerCase();
    this.word = word;
      this.status = new char[word.length()];
      for (int i = 0; i < word.length(); i++) {
        this.status[i] = '_';
      }
      return 1;
  }

  @Override
  public int getMis() {
    return this.mis;
  }

  @Override
  public boolean isOver() {
    if (this.mis == 8) {
      return true;
    }
    for (char c : this.status) {
      if (c == '_') {
        return false;
      }
    }
    return true;
  }

  @Override
  public String getStatus() {
    return new String(status);
  }

  @Override
  public int guess(char c) {
    c = Character.toLowerCase(c);
    int mark = 0;
    for (int i = 0; i < this.word.length(); i++) {
      if (this.status[i] == c) {
        return -1;
      }
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
    s = s.toLowerCase();
    if (this.word.equals(s)) {
      this.status = this.word.toCharArray();
      return 1;
    } else {
      this.mis++;
      return 0;
    }
  }

  @Override
  public String getWord() {
    return this.word;
  }
}
