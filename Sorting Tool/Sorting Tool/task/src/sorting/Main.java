package sorting;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(final String[] args) {
        SortingNumbers sortingNumbers = new SortingNumbers();
        SortingLines sortingStrings = new SortingLines();
        SortingWords sortingWords = new SortingWords();
        SortingIntegers sortingIntegers = new SortingIntegers();


        switch (parsingArguments(args)) {
            case "sorting":
                sortingIntegers.sorting();
                break;
            case "long":
                sortingNumbers.sorting();
                break;
            case "line":
                sortingStrings.sorting();
                break;
            case "word":
                sortingWords.sorting();
                break;
            default:
        }
    }

    public static String parsingArguments(String[] args) {
        String s = "!!!";

        List<String> arguments = List.of(args);
        if (arguments.contains("-sortIntegers")) {
            return "sorting";
        } else if (args.length >= 2) {
            for (int i = 0; i < args.length; i += 2) {
                if (args[i].equals("-dataType")) {
                    s = args[i + 1];
                }
            }
        }
        return s;
    }
}
