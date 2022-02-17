package sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SortingNumbers implements Sorting {

    private int totalNumbers = 0;
    private int greatestNumber = 0;
    private int percentage = 0;
    private int countEntrys = 0;
    private final List<Integer> list = new ArrayList<>();


    @Override
    public void sorting() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {
                list.add(scanner.nextInt());
            }
        } catch (Exception e) {
            System.out.println("!");
        }

        totalNumbers = list.size();
        greatestNumber = list.stream().max(Integer::compareTo).get();
        countEntrys = (int) list.stream().filter(integer -> integer == greatestNumber).count();
        percentage = (countEntrys * 100) / totalNumbers;

        System.out.printf("\nTotal numbers: %d.\nThe greatest number: %d (%d times(s), %d%%)\n"
                , totalNumbers, greatestNumber, countEntrys, percentage);
    }
}
