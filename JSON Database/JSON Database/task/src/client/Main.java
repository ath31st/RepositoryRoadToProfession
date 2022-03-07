package client;

import java.util.Scanner;

public class Main {
    private static String[] dataBase = new String[101];
    private static String[] inputStringArguments;
    private static String command = "";
    private static int index = 0;
    private static String text = "";

    public static void main(String[] args) {

    }

    public static void run() {
        while (!command.equals("exit")) {
            inputCommand();
            parsingInputString();
            executingCommand(dataBase);
        }
    }

    public static void inputCommand() {
        Scanner scanner = new Scanner(System.in);
        inputStringArguments = scanner.nextLine().split(" ", 3);
    }

    public static void parsingInputString() {
        if (inputStringArguments.length == 3) {
            command = inputStringArguments[0];
            index = Integer.parseInt(inputStringArguments[1]);
            text = inputStringArguments[2];
        } else if (inputStringArguments.length == 2) {
            command = inputStringArguments[0];
            index = Integer.parseInt(inputStringArguments[1]);
        } else if (inputStringArguments.length == 1) {
            command = inputStringArguments[0];
        }
    }

    public static void executingCommand(String[] dataBase) {
        boolean isRangeIndex = index > 0 & index < 101;
        if (command.equals("set")) {
            if (isRangeIndex) {
                dataBase[index] = text;
                System.out.println("OK");
            } else {
                System.out.println("ERROR");
            }
        } else if (command.equals("get")) {
            if ((!(dataBase[index] == null)) & isRangeIndex) {
                System.out.println(dataBase[index]);
            } else {
                System.out.println("ERROR");
            }
        } else if (command.equals("delete")) {
            if (isRangeIndex) {
                dataBase[index] = null;
                System.out.println("OK");
            } else {
                System.out.println("ERROR");
            }
        }
    }
}
