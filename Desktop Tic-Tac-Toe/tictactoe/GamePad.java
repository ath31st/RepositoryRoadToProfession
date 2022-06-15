package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

class GamePad extends JPanel {
    private final int WIDTH;
    private final int HEIGHT;
    private final int ROWS; //The game pad is assumed to be square
    private final int COLS; //ROWS must equal COLS
    private final int TARGET;
    private final Font markerFont = new Font("Ariel Black", Font.BOLD, 26);
    private String move = new String("X");
    private JButton[][] buttons;
    public enum Status {NOT_STARTED, IN_PROGRESS, X_WINS, O_WINS, DRAW};
    private Status status;
    private GamePadStatusBar statusBar;
    private boolean[] autoPlay = new boolean[2]; //Enable auto for X index 0 or O index 1
    private Random random = new Random();


    public GamePad(int width, int height, int rows, int cols) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ROWS = rows;
        this.COLS = cols;
        this.TARGET = cols;  //row = cols target full row col or diagonal


        setSize(width, height);

        buttons = new JButton[rows][cols];

        init();

        LayoutManager grlo = new GridLayout(rows, cols, 3, 3);
        setLayout(grlo);
        setVisible(true);
        reset();
    }

    public GamePad() {
        this.WIDTH = 300;
        this.HEIGHT = 300;
        this.ROWS = 3;
        this.COLS = 3;
        this.move = "X";
        this.TARGET = 3;
    }

    public void setStatusBar(GamePadStatusBar statusBar) {
        this.statusBar = statusBar;
        statusBar.refreshStatusLabel();
    }

    private void init() {
        move = "X";
        status = Status.NOT_STARTED;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setName("Button" + getLabel(row, col));
                buttons[row][col].setFont(markerFont);
                buttons[row][col].addActionListener(new ButtonActionListener(buttons[row][col], this));
                add(buttons[row][col]);
            }
        }
        enableGameButtons(false);
    }

    public void reset() {
        move = "X";
        status = Status.NOT_STARTED;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                buttons[row][col].setText(" ");
                buttons[row][col].setEnabled(false);
            }
        }
        if (statusBar != null) {
            statusBar.refreshStatusLabel();
        }
    }

    public void start() {
        this.status = Status.IN_PROGRESS;
        move = "X";
        statusBar.refreshStatusLabel();
        enableGameButtons(true);
        if (autoPlay[0] && autoPlay[1]) {
            //computer vs computer
            AutoPlayer ap = new AutoPlayer(this);
            ap.execute();
        } else {
            play();
        }
    }



    private  String getLabel(int row, int col) {
        int rowNumber = ROWS - row;
        char colChar = (char) (col + 'A');
        return new String(String.valueOf(colChar) + Integer.toString(rowNumber));
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public void setAutoPlayX(boolean autoPlay) {
        this.autoPlay[0] = autoPlay;
    }

    public void setAutoPlayO(boolean autoPlay) {
        this.autoPlay[1] = autoPlay;
    }

    public void play() {
        if (isAutoMode()) {
            autoPlay();
        }
    }

    public boolean isAutoMode() {
        if (move.equals("X") && autoPlay[0]) {
            return true;
        } else if (move.equals("O") && autoPlay[1]) {
            return  true;
        } else {
            return  false;
        }
    }

    public int getROWS() {
        return ROWS;
    }

    public int getCOLS() {
        return COLS;
    }
    public boolean isRobot(String move) {
        if (move.equals("X") && autoPlay[0]) {
            return true;
        } else if (move.equals("O") && autoPlay[1]) {
            return true;
        } else if (move.equals("X") || move.equals("O")) {
            return false;
        } else {
            JOptionPane.showMessageDialog(this, "Warning, invalid move: " + move);
            return false;
        }

    }

    private int count(String s) {
        //count max number of string in rows cols or diagonals
        int c = 0;
        int d1 = 0;
        int d2 = 0;
        int d3 = 0;
        int d4 = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (buttons[row][col].getText().equals(s)) {
                    d1 = 1 + countNE(row, col, s) + countSW(row, col, s);
                    d2 = 1 + countN(row, col, s) + countS(row, col, s);
                    d3 = 1 + countNW(row, col, s) + countSE(row, col, s);
                    d4 = 1 + countE(row, col, s) + countW(row, col, s);
                    d1 = Math.max(d1, d2);
                    d1 = Math.max(d1,d3);
                    d1 = Math.max(d1, d4);
                } else {
                    d1 = 0;
                }
                c = Math.max(c, d1);
            }
        }
        //System.out.println(s + " " + c);
        return c;
    }

    private int countNE(int row, int col, String s) {
        int count = 0;
        int r = row - 1;
        int c = col - 1;
        while (c >= 0 && r >= 0 && buttons[r][c].getText().equals(s)) {
            count++;
            r--;
            c--;
        }
        return count;
    }

    private int countN(int row, int col, String s) {
        int count = 0;
        int r = row - 1;
        while (r >= 0 && buttons[r][col].getText().equals(s)) {
            count++;
            r--;
        }
        return count;
    }

    private int countNW(int row, int col, String s) {
        int count = 0;
        int r = row - 1;
        int c = col + 1;
        while (r >= 0 && c < COLS && buttons[r][c].getText().equals(s)) {
            count++;
            r--;
            c++;
        }
        return count;
    }

    private int countE(int row, int col, String s) {
        int count = 0;
        int c = col - 1;
        while (c >= 0 && buttons[row][c].getText().equals(s)) {
            count++;
            c--;
        }
        return count;
    }

    private int countW(int row, int col, String s) {
        int count = 0;
        int c = col + 1;
        while(c < COLS && buttons[row][c].getText().equals(s)) {
            count++;
            c++;
        }
        return count;
    }

    private int countSE(int row, int col, String s) {
        int count = 0;
        int r = row + 1;
        int c = col - 1;
        while (r < ROWS && c >= 0 && buttons[r][c].getText().equals(s)) {
            count++;
            r++;
            c--;
        }
        return count;

    }
    private int countS(int row, int col, String s) {
        int count = 0;
        int r = row + 1;
        while(r < ROWS && buttons[r][col].getText().equals(s)) {
            count++;
            r++;
        }
        return count;

    }

    private int countSW(int row, int col, String s) {
        int count = 0;
        int r = row + 1;
        int c = col + 1;
        while( r < ROWS && c < COLS && buttons[r][c].getText().equals(s)) {
            count++;
            r++;
            c++;
        }
        return count;
    }

    public Status getStatus() {
        return this.status;
    }

    private boolean isBlank() {
        boolean blank = true;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!buttons[i][j].getText().equals(" ")) {
                    blank = false;
                    break;
                }
            }
            if (!blank) break;
        }
        return blank;
    }


    private boolean isFull() {
        boolean full = true;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (buttons[i][j].getText().equals(" ")) {
                    full = false;
                    break;
                }
            }
            if (!full) break;
        }
        return full;
    }



    public void checkStatus() {
        Status currentStatus = getStatus();
        if (count("X") >= TARGET) {
            status = Status.X_WINS;
        } else if (count("O") >= TARGET) {
            status = Status.O_WINS;
        } else if(isFull()) {
            status = Status.DRAW;
        }
        if (!(status == currentStatus)) {
            enableGameButtons(status == Status.IN_PROGRESS);
        }
        statusBar.refreshStatusLabel();
    }

    private void enableGameButtons(boolean enabled) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                buttons[row][col].setEnabled(enabled);
            }
        }
    }

    private void autoPlay() {

        boolean ready = false;
        int r;
        int c;
        int i = 0;
        //Play square that must be blocked
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (mustBlock(row, col)) {
                    ready = true;
                    placeMarker(row, col); //Same effect as if the user clicks the button
                    break;
                }
            }
            if (ready) break;
        }
        //Try to find square that should be blocked if not ready
        for (int row = 0; !ready && row  < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (shouldBlock(row, col)) {
                    ready = true;
                    placeMarker(row, col);
                    break;
                }
            }
            if (ready) break;
        }
        // Try to mark center or in the corners
        if (!ready && getText(ROWS / 2, COLS /2).isBlank()) {
            ready = true;
            placeMarker(ROWS / 2, COLS / 2);
        } else if (!ready && getText(0, 0).isBlank()) {
            ready = true;
            placeMarker( 0, 0);
        } else if (!ready && getText(0, COLS - 1).isBlank()) {
            ready = true;
            placeMarker( 0, COLS - 1);
        } else if (!ready && getText(ROWS -1, COLS - 1).isBlank()) {
            ready = true;
            placeMarker( ROWS -1, COLS - 1);
        } else if (!ready && getText(ROWS - 1, 0).isBlank()) {
            ready = true;
            placeMarker( ROWS - 1, 0);
        }
        //Choose random square to play
        while (!ready) {
            i++;
            //System.out.println(i);
            r = random.nextInt(ROWS);
            c = random.nextInt(COLS);
            if (buttons[r][c].getText().isBlank()) {
                ready = true;
                placeMarker(r, c);
            }
            if (i >= 20000) break;
        }


    }

    public void changeMove() {
        if (move.equals("X")) {
            move = "O";
        } else if (move.equals("O")) {
            move = "X";
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move value: " + move);
        }
    }

    public boolean mustBlock(int row, int col) {
        String btnText = buttons[row][col].getText();
        String msg;
        int countDiagonalNESW;
        int countDiagonalSENW;
        int countRow;
        int countCol;
        //System.out.println(row + " " + col);
        if (!btnText.isBlank()) {
            return false;
        } else {
            countDiagonalNESW = countNE(row, col, opponent()) + countSW(row, col, opponent());
            countDiagonalSENW = countSE(row, col, opponent()) + countNW(row, col, opponent());
            countRow = countE(row, col, opponent()) + countW(row, col, opponent());
            countCol = countN(row, col, opponent()) + countS(row, col, opponent());
            /*System.out.println();
            System.out.println("NE-SW: " + countDiagonalNESW);
            System.out.println("SE-NW: " + countDiagonalSENW);
            System.out.println("Row: " + countRow);
            System.out.println("Col: " + countCol);
             */

            if (countDiagonalNESW >= TARGET - 1) {
                return true;
            } else if (countDiagonalSENW >= TARGET - 1) {
                return true;
            } else if (countRow >= TARGET - 1) {
                return true;
            } else if (countCol >= TARGET - 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean shouldBlock(int row, int col) {
        String btnText = buttons[row][col].getText();
        String msg;
        int countDiagonalNESW;
        int countDiagonalSENW;
        int countRow;
        int countCol;
        int sum;
        if (!btnText.isBlank()) {
            return false;
        } else {
            countDiagonalNESW = countNE(row, col, opponent()) + countSW(row, col, opponent());
            countDiagonalSENW = countSE(row, col, opponent()) + countNW(row, col, opponent());
            countRow = countE(row, col, opponent()) + countW(row, col, opponent());
            countCol = countN(row, col, opponent()) + countS(row, col, opponent());
            sum = countDiagonalNESW + countDiagonalSENW + countRow + countCol;
            if (sum >= TARGET - 1) {
                return true;
            } else {
                return false;
            }
        }
    }


    private void placeMarker(int row, int col) {
        buttons[row][col].doClick();
    }

    public void setButtonTextMove(int row, int col) {
        buttons[row][col].setText(move);
    }

    public String getText(int row, int col) {
        return buttons[row][col].getText();
    }

    public String opponent() {
        //Returns the sign of the player that is not active
        if (move.equals("X")) {
            return "O";
        } else if (move.equals("O")) {
            return "X";
        } else {
            JOptionPane.showMessageDialog(this, "Warning, invalid move: " + move);
            move = "X";
            return "O";
        }
    }


}