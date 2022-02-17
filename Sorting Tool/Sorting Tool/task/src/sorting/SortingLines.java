package sorting;

import java.util.*;

public class SortingLines implements Sorting {

    private String longestLine;
    private int countEntrys = 0;
    private int percentage = 0;
    private final List<String> strings = new ArrayList<>();

    @Override
    public void sorting() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                strings.add(scanner.nextLine());
                //strings.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("!!!");
        }

        longestLine = Collections.max(strings, Comparator.comparing(String::length));
        countEntrys = (int) strings.stream().filter(e -> e.equals(longestLine)).count();
        percentage = (100 * countEntrys) / strings.size();

        System.out.printf("\nTotal lines: %d.", strings.size());
        System.out.print("\nLongest line:\n");
        System.out.println(longestLine);
        System.out.printf("(%d time(s), %d%%).", countEntrys, percentage);
    }
}
