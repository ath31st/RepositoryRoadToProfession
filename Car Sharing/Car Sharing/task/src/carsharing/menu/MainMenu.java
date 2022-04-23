package carsharing.menu;

import carsharing.dbclasses.*;

import static carsharing.menu.LocalUtils.printMenu;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    DBClass dbClass;
    private static final Scanner scanner = new Scanner(System.in);

    public MainMenu(DBClass dbClass) {
        this.dbClass = dbClass;
    }

    public List<String> mainMenu = Arrays.asList(
            "1. Log in as a manager",
            "2. Log in as a customer",
            "3. Create a customer",
            "0. Exit");

    public void start() {
        menuOperation();
    }

    public void menuOperation() {
        System.out.println();
        printMenu(mainMenu);
        String selectedMenu = scanner.nextLine().trim();
        // 1. Log in as a manager
        if ("1".equals(selectedMenu)) {
            new ManagerMenu(dbClass).start();
        }
        // 2. Log in as a customer
        if ("2".equals(selectedMenu)) {
            new CustomerMenu(dbClass).start();
        }
        // 3. Create a customer
        if ("3".equals(selectedMenu)) {
            System.out.println();
            System.out.println("Enter the customer name:");
            dbClass.insertCustomer(scanner.nextLine().trim());
            System.out.println("The customer was added!");
        }
        // 0. Exit
        if ("0".equals(selectedMenu)) {
            return;
        }
        menuOperation();
    }
}
