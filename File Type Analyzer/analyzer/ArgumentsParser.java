package analyzer;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class ArgumentsParser {
    private static final LinkedList<String[]> listPatterns = new LinkedList<>();
    private static String filePath = "";

    public static LinkedList<String[]> getListPatterns() {
        return listPatterns;
    }
    public static String getFilePath() {
        return filePath;
    }

    public static void parsing(String[] args) {
        if (args.length > 1) {
            filePath = args[0];
            String fileDB = args[1];
            File file = new File(fileDB);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    listPatterns.add(scanner.nextLine().replaceAll("\"","").split(";"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else System.out.println("Not enough arguments");
    }
}
