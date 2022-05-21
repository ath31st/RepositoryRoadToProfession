package tictactoe;

import javax.swing.*;
import java.awt.*;

public class GamePadStatusBar extends JPanel {
    private final int HEIGHT;
    private final GamePad gamePad;
    private final JLabel lblStatus;

    public GamePadStatusBar(int height, GamePad gamePad) throws HeadlessException {
        this.HEIGHT = height;
        this.gamePad = gamePad;
        setSize(gamePad.getWidth(), height);

        LayoutManager lm = new FlowLayout();
        setLayout(lm);

        lblStatus = new JLabel();

        refreshStatusLabel();

        lblStatus.setName("LabelStatus");



        add(lblStatus);


    }

    public void refreshStatusLabel() {
        String text = "";
        switch (gamePad.getStatus()) {
            case DRAW:
                text = "Draw";
                break;
            case O_WINS:
                if (gamePad.isRobot("O")) {
                    text = "The Robot ";
                } else {
                    text = "The Human ";
                }
                text += "Player (O) wins";
                break;
            case X_WINS:
                if (gamePad.isRobot("X")) {
                    text = "The Robot ";
                } else {
                    text = "The Human ";
                }
                text += "Player (X) wins";
                break;
            case IN_PROGRESS:
                text = "The turn of ";
                if (gamePad.isRobot(gamePad.getMove())) {
                    text += "Robot ";
                } else {
                    text += "Human ";
                }
                text += "Player (" + gamePad.getMove() +")";
                break;
            case NOT_STARTED:
                text = "Game is not started";
                break;
            default:
                text = "Error";
        }
        lblStatus.setText(text);
    }
}