package metro;

import metro.util.FileWithStationsNames;
import metro.util.ParserCommandLine;
import metro.util.PrintResult;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static LinkedList<String> stations = new LinkedList<>();

    public static void run(String[] args) {
        ParserCommandLine.parse(args);
        try (Scanner scanner = new Scanner(FileWithStationsNames.getFile())) {
            while (scanner.hasNextLine()) {
                stations.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error! Such a file doesn't exist!");
        }
        PrintResult.print(stations);

//        List<String> tmp = List.of("Bishops road",
//                "Edgver road",
//                "Baker street",
//                "Portland road",
//                "Gower street",
//                "Kings cross",
//                "Farringdon street");
//        LinkedList<String> tmpLL = new LinkedList<>(tmp);
//        PrintResult.print(tmpLL);
    }
}
