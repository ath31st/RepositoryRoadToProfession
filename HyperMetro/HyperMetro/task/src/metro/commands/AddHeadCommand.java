package metro.commands;

import metro.database.Subway;

public class AddHeadCommand implements Command {
    private final String lineName;
    private final String stationNameToAdd;
    private final String nextStationName;
    private final Integer time;
    private final Subway subway;

    public AddHeadCommand(String lineName, String stationNameToAppend, String nextStationName, Integer time, Subway subway) {
        this.lineName = lineName;
        this.stationNameToAdd = stationNameToAppend;
        this.nextStationName = nextStationName;
        this.time = time;
        this.subway = subway;
    }

    @Override
    public void execute() {
        subway.addHead(lineName, stationNameToAdd, nextStationName, time);
    }
}
