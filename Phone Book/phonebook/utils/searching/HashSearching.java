package phonebook.utils.searching;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HashSearching {

    public static long search() throws InterruptedException {
        long time = System.currentTimeMillis();
//        HashMap<String, String> hashContacts = new HashMap<>();
//        for (Map.Entry<String, String> entry : hashContacts.entrySet()) {
//            for (String finder : finders) {
//                entry.getValue().equals(finder);
//            }
//        }
        TimeUnit.SECONDS.sleep((long) (Math.random() * 5));

        return time = System.currentTimeMillis() - time;
    }
}
