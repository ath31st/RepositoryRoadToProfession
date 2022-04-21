package carsharing;

public class Menu {

    public void showMenu() {
        System.out.println("1. Log in as a manager\n" +
                "0. Exit");
    }

    public void showManagerMenu() {
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
    }

    public void showCompanyMenu() {
        System.out.println("Company list:\n" +
                "1. First company name\n" +
                "2. Second company name\n" +
                "3. Third company name");
    }
}
