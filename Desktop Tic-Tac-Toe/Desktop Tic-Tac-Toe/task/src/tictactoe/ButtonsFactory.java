package tictactoe;

import javax.swing.*;

public class ButtonsFactory {
    static JButton createButton(String nameButton, String textButton) {
        JButton jButton = new JButton(textButton);
        jButton.setName(nameButton);
        return jButton;
    }
}
