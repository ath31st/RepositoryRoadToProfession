package sorting;

public class Application {

    public static void run(String[] args) {

        Sorting sorting = SortingGetterType.getType(args);

        if (CommandLineParser.parser(args).endsWith("byCount")) {
            sorting.sortingByCount();
        } else sorting.sorting(); //by natural
    }
}
