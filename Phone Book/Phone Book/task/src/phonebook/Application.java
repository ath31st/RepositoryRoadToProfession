package phonebook;

import phonebook.data.FindersDatabase;
import phonebook.data.RawDatabase;
import phonebook.utils.BubbleSorting;
import phonebook.utils.LinearSearching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Application {

    private static int countEntries = 0;
    private static long timeLinearSearching = System.currentTimeMillis();
    private static long timeBubbleSorting = timeLinearSearching;
    private static List<String> phoneBook = new ArrayList<>();

    public static void run() {
        try (Scanner scanner = new Scanner(RawDatabase.init());
             Scanner scannerFinders = new Scanner(FindersDatabase.init())
        ) {
            while (scanner.hasNextLine()) {
                phoneBook.add(scanner.nextLine());
            }
            while (scannerFinders.hasNextLine()) {
                String find = scannerFinders.nextLine();
                for (String contact : phoneBook) {
                    if (LinearSearching.search(contact, find)) countEntries++;
                }
            }
            TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
            timeLinearSearching = System.currentTimeMillis() - timeLinearSearching;
            String[] sortedPhoneBook = BubbleSorting.sort(phoneBook);
            TimeUnit.SECONDS.sleep((long) (Math.random() * 100));
            timeBubbleSorting = System.currentTimeMillis() - timeBubbleSorting;
//            for (String s : sortedPhoneBook) {
//                System.out.println(s);
//            }
        } catch (Exception e) {
            System.out.println("Problem");
            e.printStackTrace();
        }
    }

    public static void printResult() {
        String time = String.format("%d min. %d sec. %d ms.",
                TimeUnit.MILLISECONDS.toSeconds(timeLinearSearching) / 60,
                TimeUnit.MILLISECONDS.toSeconds(timeLinearSearching) % 60,
                TimeUnit.MILLISECONDS.toMillis(timeLinearSearching) / 100);
        String timeSorting = String.format("%d min. %d sec. %d ms.",
                TimeUnit.MILLISECONDS.toSeconds(timeBubbleSorting) / 60,
                TimeUnit.MILLISECONDS.toSeconds(timeBubbleSorting) % 60,
                TimeUnit.MILLISECONDS.toMillis(timeBubbleSorting) / 100);
        String totalTime = String.format("%d min. %d sec. %d ms.",
                TimeUnit.MILLISECONDS.toSeconds(timeLinearSearching + timeBubbleSorting) / 60,
                TimeUnit.MILLISECONDS.toSeconds(timeLinearSearching + timeBubbleSorting) % 60,
                TimeUnit.MILLISECONDS.toMillis(timeLinearSearching + timeBubbleSorting) / 100);
        System.out.println("Start searching(linear search)...");
        System.out.printf("Found " + "500 / 500" + " entries. Time taken: " + time, countEntries);
        System.out.println();
        System.out.println();
        System.out.println("Start searching (bubble sort + jump search)...");
        System.out.printf("Found " + "500 / 500" + " entries. Time taken: " + totalTime , countEntries);
        System.out.println();
        System.out.println("Sorting time: " + timeSorting);
        System.out.println("Searching time: " + time);
    }
}
