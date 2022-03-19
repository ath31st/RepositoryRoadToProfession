package metro;

import metro.util.*;
import metro.util.commands.Command;
import metro.util.commands.CommandOutput;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Application {

    private static LinkedList<String> stations = new LinkedList<>();

    public static void run(String[] args) {
        //ParserCommandLine.parse(args);

        try (FileReader fileReader = new FileReader(FileWithStationsNames.getFile());
             Scanner scanner = new Scanner(System.in)
        ) {
            GsonParser gsonParser = new GsonParser();
            Metro metro = gsonParser.parser(fileReader);

            while (!scanner.nextLine().equals("/exit")) {
                Command command = new CommandOutput();
                command.execute();
            }

        } catch (IOException e) {
            System.out.println("Error! Such a file doesn't exist!");
        }
        PrintResult.print(stations);
    }
}
