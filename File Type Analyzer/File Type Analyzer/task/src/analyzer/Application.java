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
                try (Scanner scanner = new Scanner(file)) {
                    String text;
                    while (scanner.hasNextLine()) {
                        text = scanner.nextLine();
                        for (String[] pattens : ArgumentsParser.getListPatterns()) {
                            if (AlgorithmKMP.KMPSearch(pattens[1], text)) {
                                System.out.println(file.getName() + ": " + pattens[2]);
                            } else {
                                System.out.println(file.getName() + ": Unknown file type");
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
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

