package tracker;

import java.util.Scanner;

public class Application {
    public static void run() {
        System.out.println("Learning Progress Tracker");
        String command;
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                command = scanner.nextLine().strip();
                if (command.equals("exit")) {
                    System.out.println("Bye!");
                    break;
                }
                if (command.isBlank()) {
                    System.out.println("No input.");
                }
                if (command.length() > 0) {
                    System.out.println("Error: unknown command!");
                }

            }
        }
    }

}
