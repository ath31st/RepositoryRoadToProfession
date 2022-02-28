package analyzer;

import java.util.regex.Pattern;

public class ArgumentsParser {
    private static String nameFile = "";
    private static String documentType = "";
    private static String patternString = "";
    private static String searchingSignature = "";

    public static String getNameFile() {
        return nameFile;
    }

    public static String getDocumentType() {
        return documentType;
    }

    public static String getPatternString() {
        return patternString;
    }

    public static String getSearchingSignature() {
        return searchingSignature;
    }

    Pattern pattern = Pattern.compile("^.*\\.(jpg|JPG|gif|GIF|doc|DOC|pdf|PDF)$");

    public static void parsing(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                patternString = args[i];
            }
            if (args[i].startsWith("%") & args[i].endsWith("-")) {
                searchingSignature = args[i];
            }
            if (args[i].matches("^.*\\.(jpg|JPG|gif|GIF|doc|DOC|pdf|PDF)$")) {
                nameFile = args[i];
            }
            if (args[i].contains(" document") | args[i].contains("Unknown")) {
                documentType = args[i];
            }
        }
    }

//    public static void main(String args[]) {
//        parsing(args);
//        System.out.println(nameFile);
//        System.out.println(patternString);
//        System.out.println(searchingSignature);
//    }
}
