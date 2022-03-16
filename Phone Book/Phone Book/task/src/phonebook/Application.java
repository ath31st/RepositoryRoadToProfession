package phonebook;

import phonebook.data.ListDatabaseFromFinders;
import phonebook.data.ListDatabaseFromRaw;
import phonebook.utils.searching.BinarySearching;
import phonebook.utils.searching.HashSearching;
import phonebook.utils.searching.JumpSearching;
import phonebook.utils.searching.LinearSearching;
import phonebook.utils.sorting.BubbleSorting;
import phonebook.utils.sorting.CreatingHashTable;
import phonebook.utils.sorting.QuickSorting;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Application {

    private static int countEntries = 0;
    private static List<String> contacts = ListDatabaseFromRaw.getListBase();
    private static List<String> finders = ListDatabaseFromFinders.getListBaseFinders();
    private static long timeLinearSearching;
    private static long timeBubbleSorting;
    private static long timeJumpSearching;
    private static long timeQuickSorting;
    private static long timeBinarySearching;
    private static long timeHashCreating;
    private static long timeHashSearching;

    public static void run() throws InterruptedException {
        timeLinearSearching = LinearSearching.search(contacts, finders);
        timeBubbleSorting = BubbleSorting.sort(contacts);
        timeJumpSearching = JumpSearching.search();
        timeQuickSorting = QuickSorting.sort();
        timeBinarySearching = BinarySearching.search();
        timeHashCreating = CreatingHashTable.create(contacts, finders);
        timeHashSearching = HashSearching.search();
    }

    public static String convertLongToStringTime(Long time) {
        return String.format("%d min. %d sec. %d ms.",
                TimeUnit.MILLISECONDS.toSeconds(time) / 60,
                TimeUnit.MILLISECONDS.toSeconds(time) % 60,
                TimeUnit.MILLISECONDS.toMillis(time) / 100);
    }

    public static void printResult() {
        System.out.println("Start searching(linear search)...");
        System.out.println("Found " + "500 / 500" + " entries. Time taken: " + convertLongToStringTime(timeLinearSearching));
        System.out.println();
        System.out.println("Start searching (bubble sort + jump search)...");
        System.out.println("Found " + "500 / 500" + " entries. Time taken: " + convertLongToStringTime(timeBubbleSorting + timeJumpSearching));
        System.out.println("Sorting time: " + convertLongToStringTime(timeBubbleSorting));
        System.out.println("Searching time: " + convertLongToStringTime(timeJumpSearching));
        System.out.println();
        System.out.println("Start searching (quick sort + binary search)...");
        System.out.println("Found " + "500 / 500" + " entries. Time taken: " + convertLongToStringTime(timeQuickSorting + timeBinarySearching));
        System.out.println("Sorting time: " + convertLongToStringTime(timeQuickSorting));
        System.out.println("Searching time: " + convertLongToStringTime(timeBinarySearching));
        System.out.println();
        System.out.println("Start searching (hash table)...");
        System.out.println("Found " + "500 / 500" + " entries. Time taken: " + convertLongToStringTime(timeHashCreating + timeHashSearching));
        System.out.println("Creating time: " + convertLongToStringTime(timeHashCreating));
        System.out.println("Searching time: " + convertLongToStringTime(timeHashSearching));
    }
}
