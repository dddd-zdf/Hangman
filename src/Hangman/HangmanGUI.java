package Hangman;

import javax.swing.*;

public class HangmanGUI extends JFrame implements HangmanView {
    private JPanel mainPanel;
    public HangmanGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(this.mainPanel);
        this.pack();
    }

}
