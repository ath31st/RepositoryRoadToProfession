package carsharing;

import carsharing.db.Car;
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
                        int counter = 1;
                        for (Company company : companies) {
                            System.out.println(counter + ". " + company.getCompanyName());
                            counter++;
                        }
                        System.out.println("0. Back");
//                        String companyName = scanner.nextLine();
//                        Company company = database.findCompanyByName(companyName);
//                        if (company.getCompanyName() == null) break;
                        action = Integer.parseInt(scanner.nextLine());
                        if (action == 0) managerMenu();
                        Company company = companies.get(action - 1);
                        final String companyMenuMessage =
                                "1. Car list\n" +
                                "2. Create a car\n" +
                                "0. Back";
                        do {
                            System.out.println(companyMenuMessage);

                            action = Integer.parseInt(scanner.nextLine());
                            switch (action) {
                                case 1:
                                    List<Car> cars = database.getAllCars(company.getId());
                                    if (!cars.isEmpty()) {
                                        int counterCar = 1;
                                        for (Car car : cars) {
                                            System.out.println(counterCar + ". " + car.getCarName());
                                            counterCar++;
                                        }
                                    } else System.out.println("The car list is empty!");
                                    break;
                                case 2:
                                    System.out.println("Enter the car name:");
                                    String carName = scanner.nextLine();
                                    database.insertNewCar(carName, company.getId());
                                    System.out.println("The car was added!");
                                    break;
                                case 0:
                                    managerMenu();
                            }

                        } while (action != 0);

                    } else {
                        System.out.println("The company list is empty!");
                    }
                    break;
                }
                // Create Company
                case 2: {
                    System.out.println("Enter the company name:");
                    String companyName = scanner.nextLine();
                    database.insertNewCompany(companyName);
                    break;
                }

                case 0: {
                    break;
                }
            }


        } while (action != 0);
    }

}