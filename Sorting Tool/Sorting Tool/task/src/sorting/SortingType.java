package sorting;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SortingType implements Sorting {

    @Override
    public void sorting() {
        List<Integer> integers = InputData.inputFromConsole().stream()
                .flatMap(Pattern.compile("[^0-9-]+")::splitAsStream)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Total numbers: " + integers.size());
        System.out.print("Sorted data: ");
        integers.forEach(e -> System.out.print(e + " "));
    }

    @Override
    public void sortingByCount() {

    }
}
