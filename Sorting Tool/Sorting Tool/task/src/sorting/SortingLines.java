package sorting;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortingLines implements Sorting {

    @Override
    public void sorting() {

        List<String> strings = InputData.inputFromSource();

        System.out.printf("\nTotal lines: %d.", strings.size());
        System.out.println("Sorted data: ");
        strings.stream().sorted().forEach(System.out::println);
    }

    @Override
    public void sortingByCount() {
        List<String> list = InputData.inputFromSource();
        Map<String, Long> map = list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        LinkedHashMap<String, Long> sortedByCount = map.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Long>comparingByValue()).thenComparing(Map.Entry::getKey))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        printByCount(sortedByCount);
    }

    public static void printByCount(Map<String, Long> map) {
        int size = map.values().stream().mapToInt(Math::toIntExact).sum();
        System.out.printf("Total lines: %d.\n", size);
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            int percent = (int) (long) entry.getValue() * 100 / size;
            System.out.printf("%s: %d time(s), %d%%\n",
                    entry.getKey(), entry.getValue(), percent);
        }
    }
}
