package sorting;

public class Main {
    public static void main(final String[] args) {
        SortingNumbers sortingNumbers = new SortingNumbers();
        SortingLines sortingStrings = new SortingLines();
        SortingWords sortingWords = new SortingWords();
        //sortingNumbers.sorting();

        switch (parsingArguments(args)) {
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
        if (args.length >= 2) {
            for (int i = 0; i < args.length; i += 2) {
                if (args[i].equals("-dataType")) {
                    s = args[i + 1];
                }
            }
        }
        return s;
    }
}
