package phonebook.utils.searching;

import java.util.List;

public class LinearSearching {
    public static long search(List<String> listContacts, List<String> finders) {
        long time = System.currentTimeMillis();
        for(String contact : listContacts) {
            for (String finder : finders) {
                String tmp = contact.substring(contact.indexOf(' ') + 1);
                finder.equals(tmp);
            }
        }
        return time = System.currentTimeMillis() - time;
    }
}
