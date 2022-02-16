package sorting;

import java.util.*;

public class Main {
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();

        while (scanner.hasNextLong()) {
            list.add(scanner.nextInt());
        }
        int total = list.size();
        int i = list.stream().max(Integer::compare).get();
        long count = list.stream().filter(integer -> integer == i).count();
        System.out.printf("Total numbers: %d.\n", total);
        System.out.printf("The greatest number: %d (%d time(s)).", i, count);
    }
}
