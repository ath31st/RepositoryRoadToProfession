package carsharing;

import carsharing.db.Company;
import carsharing.db.Database;

import java.util.List;
import java.util.Scanner;

public class Carsharing {
    private final Scanner scanner = new Scanner(System.in);
    private final Database database;

    public Carsharing(String dbName) {
        database = Database.createDB(dbName);
    }

    public void run() {
        mainMenu();
        database.closeDB();
    }

    public void mainMenu() {
        final String menuMessage = "1. Log in as a manager\n" +
                "0. Exit";
        int action;
        do {
            System.out.println(menuMessage);

            action = Integer.parseInt(scanner.nextLine());
            if (action == 1) {
                managerMenu();
            }

        } while (action != 0);
    }

    public void managerMenu() {
        final String managerMenuMessage = "1. Company list\n" +
                "2. Create a company\n" +
                "0. Back";
        int action;
        do {
            System.out.println(managerMenuMessage);

            action = Integer.parseInt(scanner.nextLine());
            switch (action) {
                // Company List
                case 1: {
                    List<Company> companies = database.getAllCompanies();
                    if (!companies.isEmpty()) {
                        System.out.println("Choose a company:");
                        for (Company company : companies) {
                            System.out.println(company.getId() + ". " + company.getCompanyName());
                        }
                        String companyName = scanner.next();
                        Company company = database.findCompanyByName(companyName);
                        if (!company.getCars().isEmpty()) {
                            company.getCars().forEach(car -> System.out.println(car.getId() + ". " + car.getCarName()));
                        } else System.out.println("The car list is empty!");
                    } else {
                        System.out.println("The company list is empty!");
                    }
                    break;
                }
                // Create Company
                case 2: {
                    System.out.println("Enter the company name:");
                    String companyName = scanner.nextLine();
                    database.addCompany(companyName);
                    break;
                }

                case 0: {
                    break;
                }
            }


        } while (action != 0);
    }

}