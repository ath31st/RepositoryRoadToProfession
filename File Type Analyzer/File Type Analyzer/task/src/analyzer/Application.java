package analyzer;

import java.io.*;
import java.util.Scanner;

public class Application {

    public static void run(String[] args) {
        ArgumentsParser.parsing(args);
        File file = new File(ArgumentsParser.getNameFile());
        if (ArgumentsParser.getSearchingSignature().length() > 0) {
            try (Scanner scanner = new Scanner(file)) {
                String text;
                while (scanner.hasNextLine()) {
                    text = scanner.nextLine();
                    if (ArgumentsParser.getPatternString().contains("naive")) {
                        if (NaiveMethod.naiveSearch(ArgumentsParser.getSearchingSignature(), text)) {
                            System.out.println(ArgumentsParser.getDocumentType());
                            System.out.println("It took 0 seconds");
                        } else {
                            System.out.println("Unknown file type");
                            System.out.println("It took 0 seconds");
                        }
                    }
                    if (ArgumentsParser.getPatternString().contains("KMP")) {
                        if (AlgorithmKMP.KMPSearch(ArgumentsParser.getSearchingSignature(), text)) {
                            System.out.println(ArgumentsParser.getDocumentType());
                            System.out.println("It took 0 seconds");
                        } else {
                            System.out.println("Unknown file type");
                            System.out.println("It took 0 seconds");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("File not found!");
            }
        } else {
            System.out.println("Unknown file type");
            System.out.println("It took 0 seconds");
        }


//        System.out.println(ArgumentsParser.getSearchingSignature());
//        System.out.println(ArgumentsParser.getNameFile());
//        System.out.println(ArgumentsParser.getPatternString());
//        System.out.println(ArgumentsParser.getDocumentType());
    }
}
