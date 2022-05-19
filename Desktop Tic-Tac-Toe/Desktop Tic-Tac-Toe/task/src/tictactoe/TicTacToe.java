package tictactoe;

import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(450, 450);
        setLocationRelativeTo(null);

        JButton a1button = ButtonsFactory.createButton("ButtonA1","A1");
        JButton a2button = ButtonsFactory.createButton("ButtonA2","A2");
        JButton a3button = ButtonsFactory.createButton("ButtonA3","A3");
        JButton b1button = ButtonsFactory.createButton("ButtonB1","B1");
        JButton b2button = ButtonsFactory.createButton("ButtonB2","B2");
        JButton b3button = ButtonsFactory.createButton("ButtonB3","B3");
        JButton c1button = ButtonsFactory.createButton("ButtonC1","C1");
        JButton c2button = ButtonsFactory.createButton("ButtonC2","C2");
        JButton c3button = ButtonsFactory.createButton("ButtonC3","C3");
        add(a3button);
        add(b3button);
        add(c3button);
        add(a2button);
        add(b2button);
        add(c2button);
        add(a1button);
        add(b1button);
        add(c1button);


        setLayout(new GridLayout(3, 3));
        setVisible(true);
    }
}