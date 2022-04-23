package carsharing;

import carsharing.db.Car;
import carsharing.db.Company;
import carsharing.db.Customer;
import carsharing.db.Database;

import java.util.List;
import java.util.Scanner;

public class Carsharing {
    private final Scanner scanner = new Scanner(System.in);
    private final Database database;

    private Company company;
    private Customer customer;

    int action = 0;

    public Carsharing(String dbName) {
        database = Database.createDB(dbName);
    }

    public void run() {
        mainMenu();
        database.closeDB();
    }

    public void mainMenu() {
        final String menuMessage =
                "1. Log in as a manager\n" +
                        "2. Log in as a customer\n" +
                        "3. Create a customer\n" +
                        "0. Exit";
        int action;
        do {
            System.out.println(menuMessage);

            action = Integer.parseInt(scanner.nextLine());
            if (action == 1) {
                managerMenu();
            }
            if (action == 2) {
                customerMenu();
            }
            if (action == 3) {
                // create new customer
                System.out.println("Enter the customer name:");
                String customerName = scanner.nextLine();
                database.insertNewCustomer(customerName);
                System.out.println("The customer was added!");
                mainMenu();
            }

        } while (action != 0);
    }

    public void managerMenu() {
        final String managerMenuMessage =
                "1. Company list\n" +
                        "2. Create a company\n" +
                        "0. Back";
        do {
            System.out.println(managerMenuMessage);

            action = Integer.parseInt(scanner.nextLine());
            switch (action) {
                // Company List
                case 1: {
                    companyMenu();
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

    public void companyCarMenu() {
        final String companyMenuMessage =
                "1. Car list\n" +
                        "2. Create a car\n" +
                        "0. Back";
        do {
            System.out.println(companyMenuMessage);

            action = Integer.parseInt(scanner.nextLine());
            switch (action) {
                case 1:
                    carCompanyList();
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
    }

    public void customerMenu() {
        List<Customer> customers = database.getAllCustomers();
        if (!customers.isEmpty()) {
            System.out.println("Customer list:");
            int counter = 1;
            for (Customer customer1 : customers) {
                System.out.println(counter + ". " + customer1.getCustomerName());
                counter++;
            }
            System.out.println("0. Back");
            action = Integer.parseInt(scanner.nextLine());
            if (action == 0) managerMenu();
            customer = customers.get(action - 1);
            rentCarMenu();
        } else System.out.println("The customer list is empty!");
    }

    public void rentCarMenu() {
        final String rentMenuMessage =
                "1. Rent a car\n" +
                        "2. Return a rented car\n" +
                        "3. My rented car\n" +
                        "0. Back";
        do {
            System.out.println(rentMenuMessage);
            action = Integer.parseInt(scanner.nextLine());
            switch (action) {
                case 1:
                    companyMenu();
                    break;
                case 2:
                    break;
                case 3:
                    Car car = database.rentedCar(customer.getCustomerName());
                    if (car.getCarName() != null) {
                        System.out.println("You rented '" + car.getCarName() + "'");
                    } else {
                        System.out.println("You didn't rent a car!");
                    }
                    break;
                case 0:
                    break;
            }
        } while (action != 0);

    }

    public void companyMenu() {
        List<Company> companies = database.getAllCompanies();
        if (!companies.isEmpty()) {
            System.out.println("Choose a company:");
            int counter = 1;
            for (Company company : companies) {
                System.out.println(counter + ". " + company.getCompanyName());
                counter++;
            }
            System.out.println("0. Back");
            action = Integer.parseInt(scanner.nextLine());
            if (action == 0) managerMenu();
            company = companies.get(action - 1);
            companyCarMenu();
        } else {
            System.out.println("The company list is empty!");
        }
    }

    public void carCompanyList() {
        List<Car> cars = database.getAllCars(company.getId());
        if (!cars.isEmpty()) {
            int counterCar = 1;
            for (Car car : cars) {
                System.out.println(counterCar + ". " + car.getCarName());
                counterCar++;
            }
        } else System.out.println("The car list is empty!");
    }

}