package metro;

import metro.commands.*;
import metro.database.Subway;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filePath = args[0];
        Subway subway = new Subway(filePath);
        boolean read = subway.populateMap();
        if (!read) {
            return;
        }

        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller();
        Command command;

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("/exit")) {
                break;
            }
            String[] arr = input.split(" ", 2);
            if (arr.length == 1) {
                System.out.println("Invalid command. Input should have action and arguments.");
                continue;
            }

            InputParser parser = new InputParser(arr[0], arr[1]);
            int time;

            switch (parser.action) {
                case "/append":
                    // user input string should be "/append lineName stationNameToAppend prevStationName time"

                    if (parser.arg1 == null || parser.arg2 == null) {
                        System.out.println("Invalid command. Line name or station name to append are null.");
                        continue;
                    }
                    if (parser.arg3 == null || parser.arg4 == null) {
                        System.out.println("Invalid command. Previous station name or time is null");
                        continue;
                    }
                    try {
                        time = Integer.parseInt(parser.arg4);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command. Time field should be integer");
                        continue;
                    }

                    command = new AppendCommand(parser.arg1, parser.arg2, parser.arg3, time, subway);
                    break;

                case "/add-head":
                    // user input string should be "/add-head lineName stationNameToAdd nextStationName time"

                    if (parser.arg1 == null || parser.arg2 == null) {
                        System.out.println("Invalid command. Line name or station name to add are null.");
                        continue;
                    }
                    if (parser.arg3 == null || parser.arg4 == null) {
                        System.out.println("Invalid command. Next station name or time is null");
                        continue;
                    }
                    try {
                        time = Integer.parseInt(parser.arg4);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command. Time field should be integer");
                        continue;
                    }

                    command = new AddHeadCommand(parser.arg1, parser.arg2, parser.arg3, time, subway);
                    break;

                case "/remove":
                    // user input string should be "/remove lineName stationName"

                    if (parser.arg1 == null || parser.arg2 == null) {
                        System.out.println("Invalid command. Line or station are null.");
                        continue;
                    }

                    command = new RemoveCommand(parser.arg1, parser.arg2, subway);
                    break;

                case "/output":
                    // user input string should be "/output lineName"

                    // does not work for looped lines

                    if (parser.arg1 == null) {
                        System.out.println("Invalid command. Line name is null.");
                        continue;
                    }

                    command = new OutputCommand(parser.arg1, subway);
                    break;

                case "/connect":
                    // user input string should be "/connect lineName1 stationName1 lineName2 stationName2"

                    if (parser.arg1 == null || parser.arg2 == null) {
                        System.out.println("Invalid command. Line or station are null.");
                        continue;
                    }
                    if (parser.arg3 == null || parser.arg4 == null) {
                        System.out.println("Invalid command. Second line or station are null.");
                        continue;
                    }

                    command = new ConnectCommand(parser.arg1, parser.arg2, parser.arg3, parser.arg4, subway);
                    break;

                case "/route":
                    // user input string should be "/route lineName1 stationName1 lineName2 stationName2"

                    if (parser.arg1 == null || parser.arg2 == null) {
                        System.out.println("Invalid command. Line or station are null.");
                        continue;
                    }
                    if (parser.arg3 == null || parser.arg4 == null) {
                        System.out.println("Invalid command. Second line or station are null.");
                        continue;
                    }
                    command = new RouteCommand(parser.arg1, parser.arg2, parser.arg3, parser.arg4, subway);
                    break;

                case "/fastest-route":
                    // user input string should be "/fastest-route lineName1 stationName1 lineName2 stationName2"

                    if (parser.arg1 == null || parser.arg2 == null) {
                        System.out.println("Invalid command. Line or station are null.");
                        continue;
                    }
                    if (parser.arg3 == null || parser.arg4 == null) {
                        System.out.println("Invalid command. Second line or station are null.");
                        continue;
                    }

                    command = new FastestRouteCommand(parser.arg1, parser.arg2, parser.arg3, parser.arg4, subway);
                    break;

                default:
                    System.out.println("Invalid command. Command type is not valid.");
                    continue;
            }

            controller.setCommand(command);
            controller.executeCommand();
        }
    }
}
