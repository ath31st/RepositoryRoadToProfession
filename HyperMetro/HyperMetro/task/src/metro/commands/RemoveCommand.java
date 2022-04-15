package metro.commands;

import metro.database.Subway;

public class RemoveCommand implements Command {
    private final String lineName;
    private final String stationName;
    private final Subway subway;

    public RemoveCommand(String lineName, String stationName, Subway subway) {
        this.lineName = lineName;
        this.stationName = stationName;
        this.subway = subway;
    }

    @Override
    public void execute() {
        subway.remove(lineName, stationName);
    }
}
