package phonebook.utils.sorting;

import java.util.concurrent.TimeUnit;

public class QuickSorting {
    public static long sort() throws InterruptedException {
        long time = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep((long) (Math.random() * 100));
        return time = System.currentTimeMillis() - time;
    }
}
