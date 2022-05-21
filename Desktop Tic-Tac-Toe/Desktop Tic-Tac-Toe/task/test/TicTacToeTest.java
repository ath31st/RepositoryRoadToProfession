import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JMenuItemFixture;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.SwingTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.swing.SwingComponent;
import tictactoe.TicTacToe;

import javax.swing.JButton;
import java.text.MessageFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;
import static org.hyperskill.hstest.testcase.CheckResult.correct;

public class TicTacToeTest extends SwingTest {
    private static final String EMPTY_CELL = " ";
    private static final String MARK_X = "X";
    private static final String MARK_O = "O";
    private static final Map<String, String> GAME_STATE = Map.of(
            "--", "Game is not started",
            "H1", "The turn of Human Player (X)",
            "R1", "The turn of Robot Player (X)",
            "H2", "The turn of Human Player (O)",
            "R2", "The turn of Robot Player (O)",
            "HX", "The Human Player (X) wins",
            "RX", "The Robot Player (X) wins",
            "HO", "The Human Player (O) wins",
            "RO", "The Robot Player (O) wins",
            "DW", "Draw");

    public TicTacToeTest() {
        super(new TicTacToe());
    }

    @SwingComponent
    private JButtonFixture buttonA1;
    @SwingComponent
    private JButtonFixture buttonA2;
    @SwingComponent
    private JButtonFixture buttonA3;
    @SwingComponent
    private JButtonFixture buttonB1;
    @SwingComponent
    private JButtonFixture buttonB2;
    @SwingComponent
    private JButtonFixture buttonB3;
    @SwingComponent
    private JButtonFixture buttonC1;
    @SwingComponent
    private JButtonFixture buttonC2;
    @SwingComponent
    private JButtonFixture buttonC3;
    @SwingComponent
    private JButtonFixture buttonStartReset;
    @SwingComponent
    private JButtonFixture buttonPlayer1;
    @SwingComponent
    private JButtonFixture buttonPlayer2;
    @SwingComponent
    private JLabelFixture labelStatus;
    @SwingComponent
    private JMenuItemFixture menuGame;
    @SwingComponent
    private JMenuItemFixture menuHumanHuman;
    @SwingComponent
    private JMenuItemFixture menuHumanRobot;
    @SwingComponent
    private JMenuItemFixture menuRobotHuman;
    @SwingComponent
    private JMenuItemFixture menuRobotRobot;
    @SwingComponent
    private JMenuItemFixture menuExit;

    private Stream<JButtonFixture> cells() {
        return Stream.of(
                buttonA3, buttonB3, buttonC3,
                buttonA2, buttonB2, buttonC2,
                buttonA1, buttonB1, buttonC1
        );
    }

    private Map<String, JButtonFixture> board;

    private final List<JButton> buttons = new ArrayList<>();

    @DynamicTest(feedback = "Cells should be visible")
    CheckResult test1() {
        cells().forEach(this::requireVisible);
        cells().map(JButtonFixture::target).forEach(buttons::add);
        board = Map.of(
                "A3", buttonA3, "B3", buttonB3, "C3", buttonC3,
                "A2", buttonA2, "B2", buttonB2, "C2", buttonC2,
                "A1", buttonA1, "B1", buttonB1, "C1", buttonC1,
                "SR", buttonStartReset);
        return correct();
    }

    @DynamicTest(feedback = "Cells should be disabled before the start of the game")
    CheckResult test2() {
        cells().forEach(this::requireDisabled);
        return correct();
    }

    @DynamicTest(feedback = "All cells should be empty before the game")
    CheckResult test3() {
        cells().forEach(cell -> cell.requireText(EMPTY_CELL));
        return correct();
    }

    private int[] cols;
    private int[] rows;

    @DynamicTest(feedback = "The board should have exactly three rows and columns")
    CheckResult test4() {
        cols = buttons.stream().mapToInt(JButton::getX).distinct().sorted().toArray();
        rows = buttons.stream().mapToInt(JButton::getY).distinct().sorted().toArray();

        assertEquals(3, cols.length,
                "The board should have exactly 3 columns. "
                        + "The column coordinates are {0}, "
                        + "the buttons have {1} different column coordinates",
                Arrays.toString(cols), cols.length);

        assertEquals(3, rows.length,
                "The board should have exactly 3 rows. "
                        + "The row coordinates are {0}, "
                        + "The buttons have {0} different row coordinates",
                Arrays.toString(rows), rows.length);

        return correct();
    }

    private static final String[] ROW_NAME = new String[]{"top", "middle", "bottom"};
    private static final String[] COL_NAME = new String[]{"left", "middle", "right"};

    @DynamicTest(feedback = "The buttons are incorrectly placed on the board")
    CheckResult test5() {
        range(0, 9).forEach(index -> {

            assertEquals(rows[index / 3], buttons.get(index).getY(),
                    "The button {0} should be located in the {1} row",
                    buttons.get(index).getText(), ROW_NAME[index / 3]);

            assertEquals(cols[index % 3], buttons.get(index).getX(),
                    "The button {0} should be located in the {1} column",
                    buttons.get(index).getText(), COL_NAME[index % 3]);
        });

        return correct();
    }

    @DynamicTest(feedback = "Add a JLabel with the name of 'LabelStatus' as status bar")
    CheckResult test6() {
        labelStatus.requireVisible();
        return correct();
    }

    @DynamicTest(feedback = "The status bar should contain the following text: 'Game is not started' before the game")
    CheckResult test7() {
        labelStatus.requireText(GAME_STATE.get("--"));
        return correct();
    }

    @DynamicTest(feedback = "Add a JButton with the name of 'ButtonStartReset' and enable it")
    CheckResult test8() {
        buttonStartReset.requireEnabled();
        return correct();
    }

    @DynamicTest(feedback = "The 'ButtonStartReset' component should have the following text: 'Start' after the program starts")
    CheckResult test9() {
        buttonStartReset.requireText("Start");
        return correct();
    }

    @DynamicTest(feedback = "Once the game is started, change the 'Start' button to 'Reset'")
    CheckResult test10() {
        buttonStartReset.click();
        buttonStartReset.requireText("Reset");
        return correct();
    }

    @DynamicTest(feedback = "Cells should be enabled after the game is started")
    CheckResult test12() {
        cells().forEach(this::requireEnabled);
        return correct();
    }

    @DynamicTest(feedback = "Once the game is started, the status should indicate the first player turn")
    CheckResult test13() {
        labelStatus.requireText(GAME_STATE.get("H1"));
        return correct();
    }

    @DynamicTest(feedback = "Disable player buttons once the game has started." +
            " Expected text: 'The turn of Human Player (X)'")
    CheckResult test14() {
        buttonPlayer1.requireDisabled();
        buttonPlayer2.requireDisabled();
        return correct();
    }

    @DynamicTest(feedback = "Display 'X' after the first move" +
            " and the status should indicate the turn of the second player")
    CheckResult test15() {
        buttonA1.click();
        buttonA1.requireText(MARK_X);
        labelStatus.requireText(GAME_STATE.get("H2"));
        return correct();
    }

    @DynamicTest(feedback = "Display 'O' after the second move" +
            " and the status should indicate the turn of the first player")
    CheckResult test16() {
        buttonA3.click();
        buttonA3.requireText(MARK_O);
        labelStatus.requireText(GAME_STATE.get("H1"));
        return correct();
    }

    @DynamicTest(feedback = "The Reset button should finish the game " +
            "enable player buttons")
    CheckResult test17() {
        buttonStartReset.click();
        buttonPlayer1.requireEnabled();
        buttonPlayer2.requireEnabled();
        return correct();
    }

    @DynamicTest(feedback = "The Reset button should clear the board" +
            " and the status should indicate 'Game is not started'")
    CheckResult test19() {
        cells().forEach(cell -> cell.requireText(EMPTY_CELL));
        labelStatus.requireText(GAME_STATE.get("--"));
        return correct();
    }

    private final String[][] humanVsHuman = new String[][]{
            {"SR", "_________", "H1"},
            {"A1", "______X__", "H2"}, {"B1", "______XO_", "H1"},
            {"C3", "__X___XO_", "H2"}, {"B3", "_OX___XO_", "H1"},
            {"B2", "_OX_X_XO_", "HX"}, {"SR", "_________", "--"},

            {"SR", "_________", "H1"},
            {"B2", "____X____", "H2"}, {"A1", "____X_O__", "H1"},
            {"C1", "____X_O_X", "H2"}, {"A3", "O___X_O_X", "H1"},
            {"A2", "O__XX_O_X", "H2"}, {"C2", "O__XXOO_X", "H1"},
            {"B3", "OX_XXOO_X", "H2"}, {"B1", "OX_XXOOOX", "H1"},
            {"C3", "OXXXXOOOX", "DW"}, {"B2", "OXXXXOOOX", "DW"},
            {"B2", "OXXXXOOOX", "DW"}, {"SR", "_________", "--"},

            {"SR", "_________", "H1"},
            {"A2", "___X_____", "H2"}, {"B2", "___XO____", "H1"},
            {"A1", "___XO_X__", "H2"}, {"A3", "O__XO_X__", "H1"},
            {"C1", "O__XO_X_X", "H2"}, {"B1", "O__XO_XOX", "H1"},
            {"C2", "O__XOXXOX", "H2"}, {"B3", "OO_XOXXOX", "HO"},
            {"A3", "OO_XOXXOX", "HO"}, {"C3", "OO_XOXXOX", "HO"},
            {"C3", "OO_XOXXOX", "HO"}, {"B2", "OO_XOXXOX", "HO"},
            {"SR", "_________", "--"}, {"SR", "_________", "H1"},
            {"SR", "_________", "--"}, {"SR", "_________", "H1"},

            {"C1", "________X", "H2"}, {"B1", "_______OX", "H1"},
            {"B2", "____X__OX", "H2"}, {"C2", "____XO_OX", "H1"},
            {"A3", "X___XO_OX", "HX"}, {"B3", "X___XO_OX", "HX"},
            {"C3", "X___XO_OX", "HX"}, {"A1", "X___XO_OX", "HX"},
            {"A1", "X___XO_OX", "HX"}, {"SR", "_________", "--"},

            // Test for double click on the same cells
            {"SR", "_________", "H1"},
            {"B2", "____X____", "H2"}, {"B2", "____X____", "H2"},
            {"B2", "____X____", "H2"}, {"C1", "____X___O", "H1"},
            {"C1", "____X___O", "H1"}, {"C1", "____X___O", "H1"},
            {"SR", "_________", "--"},

    };

    @DynamicTest(data = "humanVsHuman", feedback = "Incorrect game state")
    CheckResult test20(final String cell, final String position, final String state) {
        board.get(cell).click();
        labelStatus.requireText(GAME_STATE.get(state));
        final var iter = new StringCharacterIterator(" " + position.replace('_', ' '));
        cells().forEach(c -> c.requireText(String.valueOf(iter.next())));
        return correct();
    }


    @DynamicTest(feedback = "The player buttons should be Human vs Human")
    CheckResult test30() {
        buttonPlayer1.requireText("Human");
        buttonPlayer2.requireText("Human");
        return correct();
    }

    @DynamicTest(feedback = "The player buttons should switch Human/Robot")
    CheckResult test40() {
        buttonPlayer1.click();
        buttonPlayer1.requireText("Robot");
        buttonPlayer1.click();
        buttonPlayer1.requireText("Human");

        buttonPlayer2.click();
        buttonPlayer2.requireText("Robot");
        buttonPlayer2.click();
        buttonPlayer2.requireText("Human");
        buttonPlayer2.click();
        buttonPlayer2.requireText("Robot");
        return correct();
    }

    @DynamicTest(feedback = "After selecting 'Human vs Human', the game should start. " +
            "Both buttons should be set to 'Human' and disabled. " +
            "The Start/Reset button should be set to 'Reset' and the cells should be enabled. " +
            "The status should indicate the first human player turn.")
    CheckResult test50() {
        menuHumanHuman.requireEnabled();
        menuHumanHuman.requireVisible();
        menuHumanHuman.click();
        buttonPlayer1.requireText("Human");
        buttonPlayer2.requireText("Human");
        buttonPlayer1.requireDisabled();
        buttonPlayer2.requireDisabled();
        buttonStartReset.requireText("Reset");
        cells().forEach(this::requireEnabled);
        labelStatus.requireText(GAME_STATE.get("H1"));
        return correct();
    }

    @DynamicTest(feedback = "After selecting 'Human vs Robot', the game should start. " +
            "The first player button should be set to 'Human' and disabled. " +
            "The second player button should be set to 'Robot' and disabled. " +
            "The Start/Reset button should be set to 'Reset' and the cells should be enabled. " +
            "The status should indicate the first human player turn.")
    CheckResult test60() {
        menuHumanRobot.requireEnabled();
        menuHumanRobot.requireVisible();
        menuHumanRobot.click();
        buttonPlayer1.requireText("Human");
        buttonPlayer2.requireText("Robot");
        buttonPlayer1.requireDisabled();
        buttonPlayer2.requireDisabled();
        buttonStartReset.requireText("Reset");
        cells().forEach(this::requireEnabled);
        labelStatus.requireText(GAME_STATE.get("H1"));
        return correct();
    }

    @DynamicTest(feedback = "After selecting 'Human vs Robot', the game should start. " +
            "The first player button should be set to 'Robot' and disabled. " +
            "The second player button should be set to 'Human' and disabled. " +
            "The Start/Reset button should be set to 'Reset' and the cells should be enabled.")
    CheckResult test70() {
        menuRobotHuman.requireEnabled();
        menuRobotHuman.requireVisible();
        menuRobotHuman.click();
        buttonPlayer1.requireText("Robot");
        buttonPlayer2.requireText("Human");
        buttonPlayer1.requireDisabled();
        buttonPlayer2.requireDisabled();
        buttonStartReset.requireText("Reset");
        cells().forEach(this::requireEnabled);
        return correct();
    }

    @DynamicTest(feedback = "After selecting 'Robot vs Robot', the game should start. " +
            "Both player's buttons should be set to 'Robot' and disabled. " +
            "The Start/Reset button should be set to 'Reset' and the cells should be enabled.")
    CheckResult test80() {
        menuRobotRobot.requireEnabled();
        menuRobotRobot.requireVisible();
        menuRobotRobot.click();
        buttonPlayer1.requireText("Robot");
        buttonPlayer2.requireText("Robot");
        buttonPlayer1.requireDisabled();
        buttonPlayer2.requireDisabled();
        buttonStartReset.requireText("Reset");
        return correct();
    }

    private static void assertEquals(
            final Object expected,
            final Object actual,
            final String error,
            final Object... args) {

        if (!expected.equals(actual)) {
            final var feedback = MessageFormat.format(error, args);
            throw new WrongAnswer(feedback);
        }
    }
}
