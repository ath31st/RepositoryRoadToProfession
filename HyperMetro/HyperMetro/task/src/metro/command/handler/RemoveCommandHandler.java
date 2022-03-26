package hypermetro.command.handler;

import hypermetro.Lane;
import hypermetro.command.CommandHandler;
import hypermetro.command.CommandSyntax;
import hypermetro.command.InvalidSyntaxException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveCommandHandler extends CommandHandler {

    private static CommandHandler instance = new RemoveCommandHandler();
    public static CommandHandler getInstance() {
        return instance;
    }

    private Pattern commandPattern = Pattern.compile(CommandSyntax.removeCommand);

    @Override
    public void executeCommand(Map<String, Lane> laneMap, String commandText) {
        Matcher matcher = commandPattern.matcher(commandText);
        if (!matcher.find()) throw new InvalidSyntaxException("Invalid Command!");

        String laneName = sanitizeString(matcher.group("laneName"));
        String stationName = sanitizeString(matcher.group("stationName"));

        Lane lane = laneMap.get(laneName);
        lane.remove(lane.findStation(stationName));
    }
}
