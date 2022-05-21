package tictactoe;

import javax.swing.*;
import java.util.Random;

public class AutoPlayer extends SwingWorker {

    private GamePad gamePad;
    private String move = new String("X");
    private Random random = new Random();
    private final int ROWS;
    private final int COLS;
    private final long SLEEP_TIME_MS = 2000L;

    public AutoPlayer(GamePad gamePad) {
        this.gamePad = gamePad;
        this.ROWS = gamePad.getROWS();
        this.COLS = gamePad.getCOLS();
    }

    @Override
    protected Object doInBackground() throws Exception {
        move = "X";
        gamePad.checkStatus();

        while (gamePad.getStatus() == GamePad.Status.IN_PROGRESS) {
            play();
            gamePad.checkStatus();
            if (gamePad.getStatus() == GamePad.Status.IN_PROGRESS) {
                gamePad.changeMove();
                pause();
            }
        }
        return null;
    }

    private void play() {

        boolean ready = false;
        int r;
        int c;
        int i = 0;
        // Check for positions that must be blocked
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (gamePad.mustBlock(row, col)) {
                    ready = true;
                    gamePad.setButtonTextMove(row, col);
                    break;
                }
            }
            if (ready) break;
        }
        // Check fpr positions that should be blocked
        for (int row = 0; !ready && row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (gamePad.shouldBlock(row, col)) {
                    ready = true;
                    gamePad.setButtonTextMove(row, col);
                    break;
                }
            }
            if (ready) break;
        }
        // Try to mark center or in the corners
        if (!ready && gamePad.getText(ROWS / 2, COLS /2).isBlank()) {
            ready = true;
            gamePad.setButtonTextMove(ROWS / 2, COLS / 2);
        } else if (!ready && gamePad.getText(0, 0).isBlank()) {
            ready = true;
            gamePad.setButtonTextMove(0, 0);
        } else if (!ready && gamePad.getText(0, COLS - 1).isBlank()) {
            ready = true;
            gamePad.setButtonTextMove(0, COLS - 1);
        } else if (!ready && gamePad.getText(ROWS -1, COLS - 1).isBlank()) {
            ready = true;
            gamePad.setButtonTextMove(ROWS -1, COLS - 1);
        } else if (!ready && gamePad.getText(ROWS - 1, 0).isBlank()) {
            ready = true;
            gamePad.setButtonTextMove(ROWS - 1, 0);
        }
        //If not possible mark random free square
        while (!ready) {
            i++;
            //System.out.println(i);
            r = random.nextInt(ROWS);
            c = random.nextInt(COLS);
            if (gamePad.getText(r, c).isBlank()) {
                ready = true;
                gamePad.setButtonTextMove(r, c);
            }
            if (i >= 20000) break;
        }
    }

    private void pause() {
        try {
            Thread.sleep(SLEEP_TIME_MS);
        } catch (InterruptedException ie) {
            JOptionPane.showMessageDialog(gamePad, "Warning, interrupted exception!");
        }
    }
}