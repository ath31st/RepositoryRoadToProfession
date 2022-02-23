package sorting;

public class SortingGetterType {

    public static Sorting getType(String[] args) {
        String type = CommandLineParser.parser(args);

        if (type.startsWith("line")) {
            return new SortingLines();
        } else if (type.startsWith("word")) {
            return new SortingWords();
        } else if (type.startsWith("long")) {
            return new SortingNumbers();
        }
        return new SortingType();
    }

}
