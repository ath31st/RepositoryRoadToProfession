package analyzer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] pdfSignature = {80, 70, 68, 70, 37, 80, 68, 70};
        int[] docSignature = {80, 70, 68, 70, 37, 68, 79, 67};

        String nameFile = "";
        String patternString = "";

        for (int i = 0; i < args.length; i++) {
            if (args[i].endsWith("-")) {
                patternString = args[i];
            }
            if (args[i].endsWith(".pdf") | args[i].endsWith(".txt")) {
                nameFile = args[i];
            }
        }

        boolean isPDF = true;
        boolean isDOC = true;

        try (InputStream inputStream = new FileInputStream(nameFile)) {
            int[] headerBytes = new int[8];

            for (int i = 0; i < 8; i++) {
                headerBytes[i] = inputStream.read();
                if (headerBytes[i] != pdfSignature[i]) {
                    isPDF = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (InputStream inputStream = new FileInputStream(nameFile)) {
            int[] headerBytes = new int[8];

            for (int i = 0; i < 8; i++) {
                headerBytes[i] = inputStream.read();
                if (headerBytes[i] != docSignature[i]) {
                    isDOC = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        if (isPDF) {
            System.out.println("PDF document");
        } else if (isDOC) {
            System.out.println("DOC document");
        } else System.out.println("Unknown file type");

    }
}
