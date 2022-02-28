package analyzer;

import java.io.FileInputStream;
import java.io.InputStream;

public class SearchingDOC {
    int[] signature = {80, 70, 68, 70, 37, 68, 79, 67};
    boolean isDOC = true;


    public void kmpSearch(String nameFile) {

    }


    public void naiveSearch(String nameFile) {
        try (InputStream inputStream = new FileInputStream(nameFile)) {
            int[] headerBytes = new int[8];

            for (int i = 0; i < 8; i++) {
                headerBytes[i] = inputStream.read();
                if (headerBytes[i] != signature[i]) {
                    isDOC = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void print() {
        if (isDOC) {
            System.out.println("DOC document");
        } else System.out.println("Unknown file type");
    }
}
