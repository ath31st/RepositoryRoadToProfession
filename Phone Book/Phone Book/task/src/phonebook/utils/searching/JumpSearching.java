package phonebook.utils.searching;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class JumpSearching {
    public static long search() throws InterruptedException {
        long time = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
        return time = System.currentTimeMillis() - time;
    }
}
