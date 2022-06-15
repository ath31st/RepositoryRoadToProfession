package carsharing.menu;

import carsharing.dbclasses.Company;
import carsharing.dbclasses.DBClass;
import carsharing.dbclasses.JavaLists;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static carsharing.menu.LocalUtils.printMenu;

public class ManagerMenu {
    DBClass dbClass;
    private static final Scanner scanner = new Scanner(System.in);

    public ManagerMenu(DBClass dbClass) {
        this.dbClass = dbClass;
    }

    public List<String> managerMenu = Arrays.asList(
            "1. Company list",
            "2. Create a company",
            "0. Back");

    public void start() {
        managerMenuOperation();
    }

    public void managerMenuOperation() {
        JavaLists lists = new JavaLists(dbClass);
        System.out.println();
        printMenu(managerMenu);
        String selectedMenu = scanner.nextLine().trim();
        // 1. Company list
        if ("1".equals(selectedMenu)) {
            if (lists.getAllCompanies().size() > 0) {
                choiceCompanyOperation(lists);
            } else {
                System.out.println();
                System.out.println("The company list is empty!");
            }
        }
        // Create a company
        if ("2".equals(selectedMenu)) {
            System.out.println();
            System.out.println("Enter the company name:");
            dbClass.insertCompany(scanner.nextLine().trim());
            System.out.println("The company was created!");
        }
        // 0. Back
        if ("0".equals(selectedMenu)) {
            return;
        }
        managerMenuOperation();
    }


    public void choiceCompanyOperation(JavaLists lists) {
        System.out.println();
        System.out.println("Choose the company:");
        List<Company> companyList = lists.getAllCompanies();
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
            if (choiceCompany >= 0 & choiceCompany < lists.getAllCompanies().size()) {
                List<Company> companies = lists.getAllCompanies();
                System.out.println();
                System.out.println("'" + companies.get(choiceCompany).getName() + "'" + " company");
                new CompanyMenu(dbClass).start(choiceCompany);
                return;
            }
        } catch (NumberFormatException e) {
            // do not anything
        }
        choiceCompanyOperation(lists);
    }
}
