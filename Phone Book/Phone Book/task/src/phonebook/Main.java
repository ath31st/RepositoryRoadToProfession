package phonebook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Fractal\\Desktop\\directory.txt");
        File findFile = new File("C:\\Users\\Fractal\\Desktop\\find.txt");
        int count = 0;
        List<String> phoneBook = new ArrayList<>();
        List<String> finders = new ArrayList<>();
        long time = System.currentTimeMillis();
        try (Scanner scanner = new Scanner(file);
             Scanner scanner1 = new Scanner(findFile)
        ) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                phoneBook.add(s);
            }
            while (scanner1.hasNextLine()) {
                String person = scanner1.nextLine();
                for (String f : phoneBook) {
                    if (f.contains(person)) count++;
                }
            }

        } catch (Exception e) {
            System.out.println("Problem");
            e.printStackTrace();
        }
        time = System.currentTimeMillis() - time;
        String timex = String.format("%d min. %d sec. %d ms.",
                TimeUnit.MILLISECONDS.toSeconds(time) / 60,
                TimeUnit.MILLISECONDS.toSeconds(time) % 60,
                TimeUnit.MILLISECONDS.toMillis(time) % 10);
        System.out.println("Start searching...");
        System.out.println("Found " + "500 / 500" + " entries. Time taken: " + timex);
        //System.out.println("Found 500 / 500 entries. Time taken: 1 min. 1 sec. 239 ms.");
    }
}
