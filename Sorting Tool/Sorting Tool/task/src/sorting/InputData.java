package sorting;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class InputData {

    private static List<String> list = new ArrayList<>();

    public static List<String> inputFromConsole() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            list = bufferedReader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Exception InputData");
        }
        return list;
    }

    public static List<String> inputFromFile(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            list = bufferedReader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Exception InputData");
        }
        return list;
    }
}