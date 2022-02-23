package sorting;

import java.io.File;
import java.io.IOException;

public class SortingGetterType {
    static String type;

    public static Sorting getType(String[] args) {
        type = CommandLineParser.parser(args);

        if (type.startsWith("line")) {
            return new SortingLines();
        } else if (type.startsWith("word")) {
            return new SortingWords();
        } else if (type.startsWith("long")) {
            return new SortingNumbers();
        }
        return new SortingType();
    }

    private static class SortingType implements Sorting {
        @Override
        public void sorting() {

            File file = new File("out.txt");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //System.out.println(pathIn);
            //String pathOut = type.substring(type.indexOf("pathOut:"), type.indexOf(";"));

//            List<String> strings = InputData.inputFromSource(pathIn);
//
//            System.out.printf("\nTotal lines: %d.\n", strings.size());
//            System.out.println("Sorted data: ");
//            strings.stream().sorted().forEach(System.out::println);
        }

        @Override
        public void sortingByCount() {
        }
    }
}
