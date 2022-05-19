package tictactoe;

import javax.swing.*;

import java.util.List;

import static tictactoe.Constants.*;
import static tictactoe.TicTacToe.countTurn;

public class ButtonsFactory {
    private static List<JButton> buttons;

    public static void setButtons(List<JButton> buttons) {
        ButtonsFactory.buttons = buttons;
    }

    static JButton createButton(String nameButton) {
        JButton jButton = new JButton();
        jButton.setName(nameButton);
        return jButton;
    }

    static JButton createButton(String nameButton, String textButton) {
        JButton jButton = new JButton(textButton);
        jButton.setFocusPainted(false);
        jButton.setName(nameButton);
        return jButton;
    }

    public static void resetButtonsText(List<JButton> arrButtons, JLabel status) {
        arrButtons.forEach(jButton -> jButton.setText(NULLCELL));
        countTurn = 1;
        status.setText(GAME_IS_NOT_STARTED);
    }

    public static void changeTextButton(JButton jButton, JLabel status) {
        if (countTurn % 2 == 1 & jButton.getText().equals(NULLCELL)) {
            jButton.setText(X);
            countTurn++;
        } else if (countTurn % 2 == 0 & jButton.getText().equals(NULLCELL)) {
            jButton.setText(O);
            countTurn++;
        }
        status.setText(GAME_IN_PROGRESS);
        checkField(buttons, status);
    }

    public static void checkField(List<JButton> buttons, JLabel status) {
        boolean xIsWinner;
        boolean oIsWinner;
        xIsWinner = buttons.stream().filter(jButton -> jButton.getName().endsWith("3"))
                .allMatch(jButton -> jButton.getText().equals(X)) |
                buttons.stream().filter(jButton -> jButton.getName().endsWith("2"))
                        .allMatch(jButton -> jButton.getText().equals(X)) |
                buttons.stream().filter(jButton -> jButton.getName().endsWith("1"))
                        .allMatch(jButton -> jButton.getText().equals(X)) |
                buttons.stream().filter(jButton -> jButton.getName().startsWith("ButtonA"))
                        .allMatch(jButton -> jButton.getText().equals(X)) |
                buttons.stream().filter(jButton -> jButton.getName().startsWith("ButtonB"))
                        .allMatch(jButton -> jButton.getText().equals(X)) |
                buttons.stream().filter(jButton -> jButton.getName().startsWith("ButtonC"))
                        .allMatch(jButton -> jButton.getText().equals(X)) |
                buttons.stream().filter(jButton -> jButton.getName().equals("ButtonA3") |
                                jButton.getName().equals("ButtonB2") | jButton.getName().equals("ButtonC1"))
                        .allMatch(jButton -> jButton.getText().equals(X)) |
                buttons.stream().filter(jButton -> jButton.getName().equals("ButtonA1") |
                                jButton.getName().equals("ButtonB2") | jButton.getName().equals("ButtonC3"))
                        .allMatch(jButton -> jButton.getText().equals(X));
        oIsWinner = buttons.stream().filter(jButton -> jButton.getName().endsWith("3"))
                .allMatch(jButton -> jButton.getText().equals(O)) |
                buttons.stream().filter(jButton -> jButton.getName().endsWith("2"))
                        .allMatch(jButton -> jButton.getText().equals(O)) |
                buttons.stream().filter(jButton -> jButton.getName().endsWith("1"))
                        .allMatch(jButton -> jButton.getText().equals(O)) |
                buttons.stream().filter(jButton -> jButton.getName().startsWith("ButtonA"))
                        .allMatch(jButton -> jButton.getText().equals(O)) |
                buttons.stream().filter(jButton -> jButton.getName().startsWith("ButtonB"))
                        .allMatch(jButton -> jButton.getText().equals(O)) |
                buttons.stream().filter(jButton -> jButton.getName().startsWith("ButtonC"))
                        .allMatch(jButton -> jButton.getText().equals(O)) |
                buttons.stream().filter(jButton -> jButton.getName().equals("ButtonA3") |
                                jButton.getName().equals("ButtonB2") | jButton.getName().equals("ButtonC1"))
                        .allMatch(jButton -> jButton.getText().equals(O)) |
                buttons.stream().filter(jButton -> jButton.getName().equals("ButtonA1") |
                                jButton.getName().equals("ButtonB2") | jButton.getName().equals("ButtonC3"))
                        .allMatch(jButton -> jButton.getText().equals(O));
        if (xIsWinner) {
            status.setText(X_WINS);
            countTurn = -111111;
        }
        if (oIsWinner) {
            status.setText(O_WINS);
            countTurn = -111111;
        }
        if (countTurn > 9) {
            status.setText(DRAW);
        }
    }
}
