package phonebook.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListDatabaseFromFinders {
    private static final List<String> listFinders = new ArrayList<>();

    public static List<String> getListBaseFinders() {
        try (Scanner scanner = new Scanner(FindersDatabase.init())) {
            while (scanner.hasNextLine()) {
                listFinders.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Problem");
            e.printStackTrace();
        }
        return listFinders;
    }
}
