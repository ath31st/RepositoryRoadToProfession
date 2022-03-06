package analyzer;

import java.util.Arrays;

public class ArgumentsParser {
    private static String filePath = "";
    private static String documentType = "";
    private static String searchingSignature = "";

    public static String getFilePath() {
        return filePath;
    }

    public static String getDocumentType() {
        return documentType;
    }

    public static String getSearchingSignature() {
        return searchingSignature;
    }

    public static void parsing(String[] args) {
        if (args.length > 2) {
            filePath = args[0];
            searchingSignature = args[1];
            documentType = args[2];
        } else System.out.println("Not enough arguments");
    }
}
