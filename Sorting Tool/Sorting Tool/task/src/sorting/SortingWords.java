package sorting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SortingWords implements Sorting {

    private int totalWords = 0;
    private int countEntrys = 0;
    private int percantage = 0;
    private String longestWord;

    @Override
    public void sorting() {
        List<String> words = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8)).lines()
                .flatMap(Pattern.compile("[^\\p{L}\\p{Digit}]+")::splitAsStream)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        totalWords = words.size();
        longestWord = Collections.max(words, Comparator.comparing(String::length));
        countEntrys = (int) words.stream().filter(e -> e.equals(longestWord)).count();
        percantage = (100 * countEntrys) / totalWords;

        System.out.printf("\nTotal words: %d.", totalWords);
        System.out.printf("\nThe longest word: %s (%d time(s), %d%%)", longestWord, countEntrys, percantage);
    }
}
