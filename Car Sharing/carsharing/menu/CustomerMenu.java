package carsharing.menu;

import carsharing.dbclasses.*;

import java.util.*;
import java.util.stream.Collectors;

import static carsharing.menu.LocalUtils.printMenu;

public class CustomerMenu {
    DBClass dbClass;
    JavaLists lists;
    private static final Scanner scanner = new Scanner(System.in);

    public CustomerMenu(DBClass dbClass) {
        this.dbClass = dbClass;
    }

    public List<String> customerMenu = Arrays.asList(
            "1. Rent a car",
            "2. Return a rented car",
            "3. My rented car",
            "0. Back");


    public void start() {
        customerList();
    }

    public void customerList() {
        lists = new JavaLists(dbClass);
        List<Customer> customers = lists.getAllCustomers();
        if (customers.size() > 0) {
            System.out.println();
            System.out.println("Customer list:");
            int i = 0;
            for (Customer customer : customers) {
                System.out.println(++i + ". " + customer.getName());
            }
            System.out.println("0. Back");
            String choice = scanner.nextLine().trim();
            // 0. Back
            if ("0".equals(choice)) {
                return;
            }
            try {
                // choice the customer
                int choiceCustomer = Integer.parseInt(choice) - 1;
                if (choiceCustomer >= 0 & choiceCustomer < customers.size()) {
                    while (true) {
                        lists = new JavaLists(dbClass);
                        customers = lists.getAllCustomers();
                        Customer customer = customers.get(choiceCustomer);
                        System.out.println();
                        printMenu(customerMenu);
                        String selectedMenu = scanner.nextLine().trim();
                        // Rent a car
                        if ("1".equals(selectedMenu)) {
                            if (customer.getRented_car_id() == 0) {
                                rentCar(customer);
                            } else {
                                System.out.println();
                                System.out.println("You've already rented a car!");
                            }
                        }
                        // Return a rented car
                        if ("2".equals(selectedMenu)) {
                            returnRentedCar(customer);
                        }
                        // My rented car
                        if ("3".equals(selectedMenu)) {
                            lists = new JavaLists(dbClass);
                            myRentedCar(customer);
                        }
                        // Back
                        if ("0".equals(selectedMenu)) {
                            return;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                // do not anything
            }
        } else {
            System.out.println();
            System.out.println("The customer list is empty!");
            return;
        }
        customerList();
    }

    void rentCar(Customer customer) {
        List<Company> companyList = lists.getAllCompanies();
        System.out.println();
        if (companyList.size() > 0) {
            System.out.println("Choose a company:");
            int i = 0;
            for (Company comp : companyList) {
                System.out.println(++i + ". " + comp.getName());
            }
            System.out.println("0. Back");
            String selectedMenu = scanner.nextLine().trim();
            // 0. Back
            if ("0".equals(selectedMenu)) {
                return;
            }
            try {
                // choice the company
                int choiceCompany = Integer.parseInt(selectedMenu) - 1;
                if (choiceCompany >= 0 & choiceCompany < companyList.size()) {
                    // choose a car
                    chooseCar(customer, choiceCompany, companyList.get(choiceCompany).getName());
                    return;
                }
            } catch (NumberFormatException e) {
                // do not anything
            }
        } else {
            System.out.println("The company list is empty!");
        }
        rentCar(customer);
    }

    void chooseCar(Customer customer, int choiceCompany, String nameCompany) {
        Set<Integer> rentedCars = lists.getAllCustomers().stream()
                .map(Customer::getRented_car_id)
                .collect(Collectors.toSet());
        List<Car> cars = lists.getAllCars().stream()
                .filter(car -> car.getCompany_id() == choiceCompany + 1 & !rentedCars.contains(car.getId()))
                .collect(Collectors.toList());

        if (cars.size() > 0) {
            System.out.println();
            System.out.println("Choose a car:");
            int i = 0;
            for (Car car : cars) {
                System.out.println(++i + ". " + car.getName());
            }
            System.out.println("0. Back");
            String selectedMenu = scanner.nextLine().trim();
            if ("0".equals(selectedMenu)) {
                return;
            }
            int choiceCar = Integer.parseInt(selectedMenu) - 1;
            if (choiceCar >= 0 & choiceCar < cars.size()) {
                dbClass.updateRentedCar(customer.getId(), cars.get(choiceCar).getId());
                lists = new JavaLists(dbClass);
                System.out.println();
                System.out.println("You rented '" + cars.get(choiceCar).getName() + "'");
                return;
            }
        } else {
            System.out.println();
            System.out.println("No available cars in the " + nameCompany + " company");
            return;
        }
        chooseCar(customer, choiceCompany, nameCompany);
    }

    void returnRentedCar(Customer customer) {
        System.out.println();
        if (customer.getRented_car_id() != 0) {
            dbClass.updateRentedCarIdNull(customer.getId());
            lists = new JavaLists(dbClass);
            System.out.println("You've returned a rented car!");
        } else {
            System.out.println("You didn't rent a car!");
        }
    }

    void myRentedCar(Customer customer) {
        System.out.println();
        List<Car> cars = lists.getAllCars().stream()
                .filter(c -> c.getId() == customer.getRented_car_id())
                .collect(Collectors.toList());

        if (!cars.isEmpty()) {
            Car car = cars.get(0);
            System.out.println("Your rented car:");
            System.out.println(car.getName());
            try {
                Company company = lists.getAllCompanies().stream()
                        .filter(c -> c.getId() == car.getCompany_id())
                        .collect(Collectors.toList())
                        .get(0);
                System.out.println("Company:");
                System.out.println(company.getName());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("ERROR - no company!");
            }
        } else {
            System.out.println("You didn't rent a car!");
        }
    }
}
