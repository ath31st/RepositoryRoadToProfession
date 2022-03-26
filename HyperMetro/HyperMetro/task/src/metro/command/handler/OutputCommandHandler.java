package hypermetro.command.handler;

import hypermetro.Lane;
import hypermetro.Station;
import hypermetro.command.CommandHandler;
import hypermetro.command.CommandSyntax;
import hypermetro.command.InvalidSyntaxException;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OutputCommandHandler extends CommandHandler {
    static Pattern commandPattern = Pattern.compile(CommandSyntax.outputCommand, Pattern.CASE_INSENSITIVE);

    private static CommandHandler instance = new OutputCommandHandler();

    public static CommandHandler getInstance() {
        return instance;
    }

    @Override
    public void executeCommand(Map<String, Lane> laneMap, String commandText) {
        Matcher matcher = commandPattern.matcher(commandText);
        if (!matcher.find()) throw new InvalidSyntaxException("Invalid Command!");

        String laneName = sanitizeString(matcher.group("laneName"));
        printLaneWithoutTransfers(laneMap.get(laneName));
    }

    private static void printLaneWithoutTransfers(Lane lane) {
        if (lane == null) return;

        String prevStationString = "depot";
        String nextStationString;

        Station currStation = lane.getStart();

        while (currStation != null) {
            String currStationString = currStation.getName();
            Station nextStation = currStation.getNextStation();

            if (nextStation == null) nextStationString = "depot";
            else nextStationString = nextStation.getName();

            System.out.println(String.format("%s - %s - %s", prevStationString, currStationString, nextStationString));

            prevStationString = currStationString;
            currStation = nextStation;
        }

    }

    private static void printLaneWithTransfers(Lane lane) {
        if (lane == null) return;
        System.out.println("depot");
        Station curr = lane.getStart();
        while (curr != null) {
            printStation(curr.getTransfer(), curr.getName());
            curr = curr.getNextStation();
        }
        System.out.println("depot");
    }

    private static void printStation(Map<String, List<Station>> transfer, String currStation) {
        if (transfer == null) {
            System.out.println(currStation);
            return;
        }
        for (Map.Entry<String, List<Station>> lane : transfer.entrySet()) {
            lane.getValue().forEach(station -> System.out.println(String.format("%s - %s (%s line)", currStation, station.getName(), lane.getKey())));
        }
    }
}