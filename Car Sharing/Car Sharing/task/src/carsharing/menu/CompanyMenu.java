package carsharing.menu;

import carsharing.dbclasses.Car;
import carsharing.dbclasses.DBClass;
import carsharing.dbclasses.JavaLists;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static carsharing.menu.LocalUtils.printMenu;

public class CompanyMenu {
    DBClass dbClass;
    private static final Scanner scanner = new Scanner(System.in);

    public CompanyMenu(DBClass dbClass) {
        this.dbClass = dbClass;
    }

    public List<String> companyMenu = Arrays.asList(
            "1. Car list",
            "2. Create a car",
            "0. Back");

    public void start(int choiceCompany) {
        companyService(choiceCompany);
    }


    public void companyService(int choiceCompany) {
        JavaLists lists = new JavaLists(dbClass);
        printMenu(companyMenu);
        String selectedMenu = scanner.nextLine().trim();
        // 1. Car list
        if ("1".equals(selectedMenu)) {
            List<Car> cars = lists.getAllCars().stream()
                    .filter(car -> car.getCompany_id() == choiceCompany + 1)
                    .collect(Collectors.toList());
            if (cars.size() > 0) {
                System.out.println();
                System.out.println("Car list:");
                int i = 0;
                for (Car car : cars) {
                    System.out.println(++i + ". " + car.getName());
                }
            } else {
                System.out.println();
                System.out.println("The car list is empty!");
            }
        }
        // 2. Create a car
        if ("2".equals(selectedMenu)) {
            System.out.println();
            System.out.println("Enter the car name:");
            String newCar = scanner.nextLine().trim();
            dbClass.insertCar(newCar, choiceCompany + 1);
            System.out.println("The car was added!");
        }
        // 0. Back
        if ("0".equals(selectedMenu)) {
            return;
        }
        System.out.println();
        companyService(choiceCompany);
    }
}
