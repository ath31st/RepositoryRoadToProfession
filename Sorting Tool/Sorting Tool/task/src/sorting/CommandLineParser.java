package sorting;

import java.util.HashMap;
import java.util.Map;

class CommandLineParser {
    public static String parser(String[] args) {
        Map<String, String> map = new HashMap<>();
        if (args.length >= 2)
            for (int i = 1; i < args.length; i++) {
                if (!args[i].startsWith("-") & args[i - 1].startsWith("-")) {
                    map.put(args[i - 1], args[i]);
                }
            }
        if (map.containsKey("-dataType") & !map.containsKey("-sortingType")) {
            return map.get("-dataType") + "natural";
        } else if (map.containsKey("-dataType") & map.containsKey("-sortingType")) {
            return map.get("-dataType") + map.get("-sortingType");
        } else if (map.containsKey("-sortingType") & map.containsKey("-inputFile") & !map.containsKey("-outputFile")) {
            return map.get("-sortingType") + "pathIn:" + map.get("-inputFile");
        } else if (map.containsKey("-sortingType") & map.containsKey("-inputFile") & map.containsKey("-outputFile")) {
            return map.get("-sortingType") + "pathIn:" + map.get("-inputFile") + ";" + "pathOut:" + map.get("-outputFile") + ";";
        } else return "Wrong arguments";
    }
}