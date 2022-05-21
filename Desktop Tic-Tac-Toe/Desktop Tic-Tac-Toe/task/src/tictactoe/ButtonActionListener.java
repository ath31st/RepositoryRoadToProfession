package tictactoe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonActionListener implements ActionListener {

    JButton button;
    GamePad gamePad;

    public ButtonActionListener(JButton button, GamePad gamePad) {
        this.button = button;
        this.gamePad = gamePad;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        GamePad.Status status = gamePad.getStatus();
        if (status == GamePad.Status.IN_PROGRESS) {
            if (button.getText().isBlank()) {
                button.setText(gamePad.getMove());
                gamePad.changeMove(); //next player!
            }
            gamePad.checkStatus();
            status = gamePad.getStatus();

            if (status == GamePad.Status.IN_PROGRESS) {
                gamePad.play();
            }

        }
    }

}