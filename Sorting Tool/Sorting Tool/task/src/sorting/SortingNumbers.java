package sorting;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class SortingNumbers implements Sorting {

    @Override
    public void sorting() {
        List<String> list = InputData.inputFromSource();
        List<Integer> listInt = list.stream()
                .flatMap(Pattern.compile("[^0-9-]+")::splitAsStream)
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());
        System.out.printf("Total numbers: %d.\n", listInt.size());
        System.out.print("Sorted data: ");
        listInt.forEach(s -> System.out.print(s + " "));
    }

    @Override
    public void sortingByCount() {
        List<String> list = InputData.inputFromSource();
        Map<Integer, Long> map = list.stream()
                .flatMap(Pattern.compile("[^0-9-]+")::splitAsStream)
                .map(Integer::parseInt)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        LinkedHashMap<Integer, Long> sortedByCount = map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        printByCount(sortedByCount);
    }

    public static void printByCount(Map<Integer, Long> map) {
        int size = map.values().stream().mapToInt(Math::toIntExact).sum();
        System.out.printf("Total numbers: %d.\n", size);
        for (Map.Entry<Integer, Long> entry : map.entrySet()) {
            int percent = (int) (long) entry.getValue() * 100 / size;
            System.out.printf("%d: %d time(s), %d%%\n",
                    entry.getKey(), entry.getValue(), percent);
        }
    }
}
