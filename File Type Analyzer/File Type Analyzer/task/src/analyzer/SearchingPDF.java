package analyzer;

import java.io.FileInputStream;
import java.io.InputStream;

public class SearchingPDF{
    int[] signature = {80, 70, 68, 70, 37, 80, 68, 70}; //PDF Signature
    boolean isPDF = true;


    public void kmpSearch(String nameFile) {

    }


    public void naiveSearch(String nameFile) {
        try (InputStream inputStream = new FileInputStream(nameFile)) {
            int[] headerBytes = new int[8];

            for (int i = 0; i < 8; i++) {
                headerBytes[i] = inputStream.read();
                if (headerBytes[i] != signature[i]) {
                    isPDF = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print() {
        if (isPDF) {
            System.out.println("PDF document");
            System.out.println("It took %d seconds");
        } else System.out.println("Unknown file type");
    }
}
