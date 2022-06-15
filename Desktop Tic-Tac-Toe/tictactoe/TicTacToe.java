package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private final int GAME_PAD_WIDTH = 300;
    private final int GAME_PAD_HEIGHT = 300;
    private final int GAME_PAD_ROWS = 3;
    private final int GAME_PAD_COLS = 3;
    private final int STATUS_BAR_HEIGHT = 30;
    JButton btnPlayerX;
    JButton btnPlayerO;
    JButton btnStartReset;
    GamePad gp;




    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GAME_PAD_WIDTH, GAME_PAD_HEIGHT + 2 * STATUS_BAR_HEIGHT);
        setTitle("Tic Tac Toe");
        setLocationRelativeTo(null);
        setVisible(true);

        LayoutManager lom = new BorderLayout();
        setLayout(lom);

        gp = new GamePad(GAME_PAD_WIDTH, GAME_PAD_HEIGHT, GAME_PAD_ROWS, GAME_PAD_COLS);
        add(gp, BorderLayout.CENTER);

        GamePadStatusBar gamePadStatusBar = new GamePadStatusBar(30, gp);
        gp.setStatusBar(gamePadStatusBar);
        add(gamePadStatusBar, BorderLayout.SOUTH);

        JPanel topBar = new JPanel(new GridLayout(1, 3, 3, 3));
        btnPlayerX = new JButton();
        btnPlayerX.setName("ButtonPlayer1");
        btnPlayerX.setText("Human");
        btnPlayerX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (btnPlayerX.getText()) {
                    case "Human":
                        btnPlayerX.setText("Robot");
                        gp.setAutoPlayX(true);
                        break;
                    case "Robot":
                        btnPlayerX.setText("Human");
                        gp.setAutoPlayX(false);
                        break;
                    default:
                        JOptionPane.showMessageDialog(TicTacToe.this,
                                "Invalid button text: " + btnPlayerX.getText());
                        btnPlayerX.setText("Human");
                        gp.setAutoPlayX(false);
                }
            }
        });

        btnPlayerO = new JButton();
        btnPlayerO.setName("ButtonPlayer2");
        btnPlayerO.setText("Human");
        btnPlayerO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (btnPlayerO.getText()) {
                    case "Human":
                        btnPlayerO.setText("Robot");
                        gp.setAutoPlayO(true);
                        break;
                    case "Robot":
                        btnPlayerO.setText("Human");
                        gp.setAutoPlayO(false);
                        break;
                    default:
                        JOptionPane.showMessageDialog(TicTacToe.this,
                                "Invalid button text: " + btnPlayerO.getText());
                        btnPlayerO.setText("Human");
                        gp.setAutoPlayO(false);
                }

            }
        } );

        btnStartReset = new JButton();
        btnStartReset.setName("ButtonStartReset");
        btnStartReset.setText("Start");
        btnStartReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (btnStartReset.getText()) {
                    case "Start":
                        btnStartReset.setText("Reset");
                        btnPlayerX.setEnabled(false);
                        btnPlayerO.setEnabled(false);
                        gp.start();
                        break;
                    case "Reset":
                        btnStartReset.setText("Start");
                        btnPlayerX.setEnabled(true);
                        btnPlayerO.setEnabled(true);
                        gp.reset();
                        break;
                    default:
                        JOptionPane.showMessageDialog(TicTacToe.this,
                                "Invalid button text: " + btnStartReset.getText());
                        btnStartReset.setText("Start");
                        btnPlayerX.setEnabled(true);
                        btnPlayerO.setEnabled(true);
                        gp.reset();
                }
            }
        });

        topBar.add(btnPlayerX);
        topBar.add(btnStartReset);
        topBar.add(btnPlayerO);
        add(topBar, BorderLayout.NORTH);







        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setName("MenuGame");

        JMenuItem humanHuman = new JMenuItem("Human vs Human");
        humanHuman.setActionCommand("Human vs Human");
        humanHuman.setName("MenuHumanHuman");
        humanHuman.addActionListener(this);

        JMenuItem humanRobot = new JMenuItem("Human vs Robot");
        humanRobot.setActionCommand("Human vs Robot");
        humanRobot.setName("MenuHumanRobot");
        humanRobot.addActionListener(this);

        JMenuItem robotHuman = new JMenuItem("Robot vs Human");
        robotHuman.setActionCommand("Robot vs Human");
        robotHuman.setName("MenuRobotHuman");
        robotHuman.addActionListener(this);

        JMenuItem robotRobot = new JMenuItem("Robot vs Robot");
        robotRobot.setActionCommand("Robot vs Robot");
        robotRobot.setName("MenuRobotRobot");
        robotRobot.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setName("MenuExit");
        exit.addActionListener(this);

        gameMenu.add(humanHuman);
        gameMenu.add(humanRobot);
        gameMenu.add(robotHuman);
        gameMenu.add(robotRobot);
        gameMenu.addSeparator();
        gameMenu.add(exit);
        menuBar.add(gameMenu);
        gameMenu.setVisible(true);
        menuBar.setVisible(true);
        setJMenuBar(menuBar);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selection = e.getActionCommand();
        switch (selection) {
            case "Human vs Human":
                startGame(false, false);
                break;
            case "Human vs Robot":
                startGame(false, true);
                break;
            case "Robot vs Human":
                startGame(true, false);
                break;
            case "Robot vs Robot":
                startGame(true, true);
                break;
            case "Exit":
                this.dispose();
                break;
            default:
                JOptionPane.showMessageDialog(TicTacToe.this, "Warning, invalid menu action!");
        }

    }

    private void startGame(boolean X_IsRobot, boolean O_IsRobot) {
        //Used for starting game via menu
        if (X_IsRobot) {
            btnPlayerX.setText("Robot");
            gp.setAutoPlayX(true);
        } else {
            btnPlayerX.setText("Human");
            gp.setAutoPlayX(false);
        }
        if (O_IsRobot) {
            btnPlayerO.setText("Robot");
            gp.setAutoPlayO(true);
        } else {
            btnPlayerO.setText("Human");
            gp.setAutoPlayO(false);
        }
        gp.reset();
        gp.start();
        btnStartReset.setText("Reset");
        btnStartReset.setEnabled(true);
        btnPlayerO.setEnabled(false);
        btnPlayerX.setEnabled(false);
    }
}