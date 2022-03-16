package phonebook.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListDatabaseFromRaw {
    private static final List<String> listPhoneBook = new ArrayList<>();

    public static List<String> getListBase() {
        try (Scanner scanner = new Scanner(RawDatabase.init())) {
            while (scanner.hasNextLine()) {
                listPhoneBook.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Problem");
            e.printStackTrace();
        }
        return listPhoneBook;
    }
}
