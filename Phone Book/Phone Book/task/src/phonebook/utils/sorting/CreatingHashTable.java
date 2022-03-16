package phonebook.utils.sorting;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreatingHashTable {
    public static long create(List<String> listContacts, List<String> finders) {
        long time = System.currentTimeMillis();

        Map<String, String> hashPhoneBook = listContacts.stream()
                .map(s -> s.split(" ", 2))
                .collect(Collectors.toMap(strings -> strings[0], strings -> strings[1]));

        return time = System.currentTimeMillis() - time;
    }
}
