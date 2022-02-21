package sorting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SortingIntegers implements Sorting {

    private List<Integer> integers = new ArrayList<>();

    @Override
    public void sorting() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            integers = bufferedReader.lines()
                    .flatMap(Pattern.compile("[^0-9-]+")::splitAsStream)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .sorted()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("SortingIntegers exception");
        }
        System.out.println("Total numbers: " + integers.size());
        System.out.print("Sorted data: ");
        integers.forEach(e -> System.out.print(e + " "));
    }
}
