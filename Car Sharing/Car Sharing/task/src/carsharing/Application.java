package carsharing;

import java.util.Scanner;

public class Application {
    public static void run(String[] args) {

        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            menu.showMenu();
            int choice = scanner.nextInt();
            if (choice == 1) {
                while (true) {
                    menu.showManagerMenu();
                    choice = scanner.nextInt();
                    if (choice == 1) {
                        menu.showCompanyMenu();
                        choice = scanner.nextInt();
                        if (choice == 1) {
                            // 1. First company name
                        } else if (choice == 2) {
                            // 2. Second company name
                        } else if (choice == 3) {
                            // 3. Third company name
                        }
                    } else if (choice == 2) {
                        // create company logic
                    } else if (choice == 0) {
                        break;
                    }
                }

            } else if (choice == 0) {
                break;
            }
        }
    }
}
