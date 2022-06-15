package sorting;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SortingWords implements Sorting {

    private String longestWord;

    @Override
    public void sorting() {

        List<String> words = InputData.inputFromSource()
                .stream()
                .flatMap(Pattern.compile("[^\\p{L}\\p{Digit}]+")::splitAsStream)
                .filter(s -> !s.isEmpty())
                .sorted()
                .collect(Collectors.toList());

        System.out.printf("\nTotal words: %d.", words.size());
        System.out.print("Sorted data: ");
        words.forEach(s -> System.out.print(s + " "));

    }

    @Override
    public void sortingByCount() {
        List<String> list = InputData.inputFromSource();
        Map<String, Long> map = list.stream()
                .flatMap(Pattern.compile("[^\\p{L}\\p{Digit}-]+")::splitAsStream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        LinkedHashMap<String , Long> sortedByCount = map.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Long>comparingByValue()).thenComparing(Map.Entry::getKey))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        printByCount(sortedByCount);
    }
    public static void printByCount(Map<String, Long> map) {
        int size = map.values().stream().mapToInt(Math::toIntExact).sum();
        System.out.printf("Total words: %d.\n", size);
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            int percent = (int) (long) entry.getValue() * 100 / size;
            System.out.printf("%s: %d time(s), %d%%\n",
                    entry.getKey(), entry.getValue(), percent);
        }
    }
}
