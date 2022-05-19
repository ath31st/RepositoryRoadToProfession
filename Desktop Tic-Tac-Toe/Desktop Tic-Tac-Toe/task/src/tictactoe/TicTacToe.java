package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static tictactoe.Constants.*;

public class TicTacToe extends JFrame {

    static int countTurn = 1;
    static String status = GAME_IS_NOT_STARTED;
    static List<JButton> buttons = new ArrayList<>();

    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(450, 480);
        setLocationRelativeTo(null);

        JButton a1button = ButtonsFactory.createButton("ButtonA1", NULLCELL);
        JButton a2button = ButtonsFactory.createButton("ButtonA2", NULLCELL);
        JButton a3button = ButtonsFactory.createButton("ButtonA3", NULLCELL);
        JButton b1button = ButtonsFactory.createButton("ButtonB1", NULLCELL);
        JButton b2button = ButtonsFactory.createButton("ButtonB2", NULLCELL);
        JButton b3button = ButtonsFactory.createButton("ButtonB3", NULLCELL);
        JButton c1button = ButtonsFactory.createButton("ButtonC1", NULLCELL);
        JButton c2button = ButtonsFactory.createButton("ButtonC2", NULLCELL);
        JButton c3button = ButtonsFactory.createButton("ButtonC3", NULLCELL);

        add(a3button);
        add(b3button);
        add(c3button);
        add(a2button);
        add(b2button);
        add(c2button);
        add(a1button);
        add(b1button);
        add(c1button);
        buttons.add(a3button);
        buttons.add(a2button);
        buttons.add(a1button);
        buttons.add(b3button);
        buttons.add(b2button);
        buttons.add(b1button);
        buttons.add(c3button);
        buttons.add(c2button);
        buttons.add(c1button);
        ButtonsFactory.setButtons(buttons);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setName("LabelStatus");
        add(statusLabel);

        JButton resetButton = ButtonsFactory.createButton("ButtonReset", "Reset");
        resetButton.setVisible(true);
        add(resetButton);

        resetButton.addActionListener(actionEvent -> ButtonsFactory.resetButtonsText(buttons,statusLabel));

        a1button.addActionListener(actionEvent -> ButtonsFactory.changeTextButton(a1button, statusLabel));
        a2button.addActionListener(actionEvent -> ButtonsFactory.changeTextButton(a2button,statusLabel));
        a3button.addActionListener(actionEvent -> ButtonsFactory.changeTextButton(a3button,statusLabel));
        b1button.addActionListener(actionEvent -> ButtonsFactory.changeTextButton(b1button,statusLabel));
        b2button.addActionListener(actionEvent -> ButtonsFactory.changeTextButton(b2button,statusLabel));
        b3button.addActionListener(actionEvent -> ButtonsFactory.changeTextButton(b3button,statusLabel));
        c1button.addActionListener(actionEvent -> ButtonsFactory.changeTextButton(c1button,statusLabel));
        c2button.addActionListener(actionEvent -> ButtonsFactory.changeTextButton(c2button,statusLabel));
        c3button.addActionListener(actionEvent -> ButtonsFactory.changeTextButton(c3button,statusLabel));


        GridLayout gridLayout = new GridLayout(4, 3);
        setLayout(gridLayout);
        setVisible(true);
    }
}