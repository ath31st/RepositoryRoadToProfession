import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JLabelFixture;
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
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;
import static org.hyperskill.hstest.testcase.CheckResult.correct;
import static org.hyperskill.hstest.testcase.CheckResult.wrong;




class OsCheck {

    public static void main(String[] args) {
        System.out.println(
            System.getProperty(
                "os.name", "generic")
                .toLowerCase(Locale.ENGLISH)
        );
    }

    /**
     * types of Operating Systems
     */
    public enum OSType {
        Windows, MacOS, Linux, Other
    };

    // cached result of OS detection
    protected static OSType detectedOS;

    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty(
                "os.name", "generic")
                .toLowerCase(Locale.ENGLISH);
            if ((OS.contains("mac"))
                || (OS.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            } else if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (OS.contains("nux")) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }
}

public class TicTacToeTest extends SwingTest {
    private static final String EMPTY_CELL = " ";
    private static final String MARK_X = "X";
    private static final String MARK_O = "O";
    private static final Map<String, String> GAME_STATE = Map.of(
            "E", "Game is not started",
            "P", "Game in progress",
            "X", "X wins",
            "O", "O wins",
            "D", "Draw");

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
    private JButtonFixture buttonReset;
    @SwingComponent
    private JLabelFixture labelStatus;

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
                "RS", buttonReset);
        return correct();
    }

    @DynamicTest(feedback = "Cells should be enabled")
    CheckResult test2() {
        cells().forEach(this::requireEnabled);
        return correct();
    }

    @DynamicTest(feedback = "All cells should be empty before the game starts")
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
                    "The button {0} should be located on the {1} row",
                    buttons.get(index).getText(), ROW_NAME[index / 3]);

            assertEquals(cols[index % 3], buttons.get(index).getX(),
                    "The button {0} should be located on the {1} column",
                    buttons.get(index).getText(), COL_NAME[index % 3]);
        });

        return correct();
    }

    @DynamicTest(feedback = "Add a JLabel with the name of 'LabelStatus' as status bar")
    CheckResult test6() {
        labelStatus.requireVisible();
        return correct();
    }

    @DynamicTest(feedback = "The status bar should contain the following text: 'Game is not started' before the game starts")
    CheckResult test7() {
        labelStatus.requireText(GAME_STATE.get("E"));
        return correct();
    }

    @DynamicTest(feedback = "Add a JButton with the name of 'ButtonReset' and enable it")
    CheckResult test8() {
        buttonReset.requireEnabled();
        return correct();
    }

    @DynamicTest(feedback = "After the first click on the A1 cell, this cell should contain the X symbol.")
    CheckResult test9() {
        try {
            buttonA1.click();
            buttonA1.requireText(MARK_X);
            return correct();
        } catch (Throwable ex) {
            if (OsCheck.getOperatingSystemType() == OsCheck.OSType.MacOS) {
                return wrong(
                    "Please, make sure that Intellij Idea has access to control your mouse and keyboard: \n" +
                    "go to System Preferences -> Security & Privacy -> Accessibility\n" +
                    "and grant Intellij IDEA access to control your computer.");
            }
            throw ex;
        }
    }

    @DynamicTest(feedback = "After the second click on the A3 cell, this cell should contain the O symbol.")
    CheckResult test10() {
        buttonA3.click();
        buttonA3.requireText(MARK_O);
        return correct();
    }

    @DynamicTest(feedback = "The Reset board should clear the board")
    CheckResult test12() {
        buttonReset.click();
        cells().forEach(cell -> cell.requireText(EMPTY_CELL));
        labelStatus.requireText(GAME_STATE.get("E"));
        return correct();
    }

    private final String[][] humanVsHuman = new String[][]{
            {"A1", "______X__", "P"}, {"B1", "______XO_", "P"},
            {"C3", "__X___XO_", "P"}, {"B3", "_OX___XO_", "P"},
            {"B2", "_OX_X_XO_", "X"}, {"RS", "_________", "E"},

            {"B2", "____X____", "P"}, {"A1", "____X_O__", "P"},
            {"C1", "____X_O_X", "P"}, {"A3", "O___X_O_X", "P"},
            {"A2", "O__XX_O_X", "P"}, {"C2", "O__XXOO_X", "P"},
            {"B3", "OX_XXOO_X", "P"}, {"B1", "OX_XXOOOX", "P"},
            {"C3", "OXXXXOOOX", "D"}, {"B2", "OXXXXOOOX", "D"},
            {"B2", "OXXXXOOOX", "D"}, {"RS", "_________", "E"},

            {"A2", "___X_____", "P"}, {"B2", "___XO____", "P"},
            {"A1", "___XO_X__", "P"}, {"A3", "O__XO_X__", "P"},
            {"C1", "O__XO_X_X", "P"}, {"B1", "O__XO_XOX", "P"},
            {"C2", "O__XOXXOX", "P"}, {"B3", "OO_XOXXOX", "O"},
            {"A3", "OO_XOXXOX", "O"}, {"C3", "OO_XOXXOX", "O"},
            {"C3", "OO_XOXXOX", "O"}, {"B2", "OO_XOXXOX", "O"},
            {"RS", "_________", "E"}, {"RS", "_________", "E"},

            {"C1", "________X", "P"}, {"B1", "_______OX", "P"},
            {"B2", "____X__OX", "P"}, {"C2", "____XO_OX", "P"},
            {"A3", "X___XO_OX", "X"}, {"B3", "X___XO_OX", "X"},
            {"C3", "X___XO_OX", "X"}, {"A1", "X___XO_OX", "X"},
            {"A1", "X___XO_OX", "X"}, {"RS", "_________", "E"},
    };

    @DynamicTest(data = "humanVsHuman", feedback = "Incorrect game state")
    CheckResult test20(final String cell, final String position, final String state) {
        board.get(cell).click();
        labelStatus.requireText(GAME_STATE.get(state));
        final var iter = new StringCharacterIterator(" " + position.replace('_', ' '));
        cells().forEach(c -> c.requireText(String.valueOf(iter.next())));
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
