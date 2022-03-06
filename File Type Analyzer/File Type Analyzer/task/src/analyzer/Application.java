package analyzer;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Application {

    public static void run(String[] args) {
        ArgumentsParser.parsing(args);
        File[] folder = new File(ArgumentsParser.getFilePath()).listFiles();
        assert folder != null;
        List<File> files = Arrays.stream(folder)
                .filter(File::isFile)
                .collect(Collectors.toList());

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (File file : files) {
            executor.submit(() -> {
                if (ArgumentsParser.getSearchingSignature().length() > 0) {
                    try (Scanner scanner = new Scanner(file)) {
                        String text;
                        while (scanner.hasNextLine()) {
                            text = scanner.nextLine();
                            if (AlgorithmKMP.KMPSearch(ArgumentsParser.getSearchingSignature(), text)) {
                                System.out.println(file.getName() + ": " + ArgumentsParser.getDocumentType());
                            } else {
                                System.out.println(file.getName() + ": Unknown file type");
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Unknown folder type");
                }
            });
        }

        executor.shutdown();
        while (true) {
            try {
                if (executor.awaitTermination(24L, TimeUnit.HOURS)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Not yet. Still waiting for termination");
        }
    }
}

