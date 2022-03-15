package phonebook.utils;

import java.util.List;

public class BubbleSorting {

    public static String[] sort(List<String> list) {
        String[] phoneBook = list.toArray(new String[0]);
        for (int i = 0; i < phoneBook.length; i++) {
            for (int j = 1; j < phoneBook.length - i; j++) {
                String contact1 = phoneBook[j - 1].substring(phoneBook[j - 1].indexOf(' ') + 1);
                String contact2 = phoneBook[j].substring(phoneBook[j].indexOf(' ') + 1);
                if (contact1.compareTo(contact2) > 0) {
                    String tempStr = phoneBook[j - 1];
                    phoneBook[j - 1] = phoneBook[j];
                    phoneBook[j] = tempStr;
                }
            }
        }
        return phoneBook;
    }
}
