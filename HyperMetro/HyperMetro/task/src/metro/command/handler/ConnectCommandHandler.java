package hypermetro.command.handler;

import hypermetro.Lane;
import hypermetro.Station;
import hypermetro.command.CommandHandler;
import hypermetro.command.CommandSyntax;
import hypermetro.command.InvalidSyntaxException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectCommandHandler extends CommandHandler {

    private static CommandHandler instance = new ConnectCommandHandler();
    public static CommandHandler getInstance() {
        return instance;
    }

    private static Pattern commandPattern = Pattern.compile(CommandSyntax.connectCommand);

    @Override
    public void executeCommand(Map<String, Lane> laneMap, String commandText) {
        Matcher matcher = commandPattern.matcher(commandText);

        if (!matcher.find()) throw new InvalidSyntaxException("Invalid Command!");
        String fromLaneString =  sanitizeString(matcher.group("lane1"));
        String toLaneString = sanitizeString(matcher.group("lane2"));
        String fromStationString = sanitizeString(matcher.group("station1"));
        String toStationString = sanitizeString(matcher.group("station2"));

        Lane fromLane = laneMap.get(fromLaneString);
        Lane toLane = laneMap.get(toLaneString);

        if (fromLane == null || toLane == null) throw new IllegalArgumentException("Lane not found!");

        Station fromStation = fromLane.findStation(fromStationString);
        Station toStation = toLane.findStation(toStationString);

        fromStation.addTransfer(toLaneString, toStation);
        toStation.addTransfer(fromLaneString, fromStation);
    }
}
