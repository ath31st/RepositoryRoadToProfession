package metro.commands;

import metro.database.Subway;

public class AppendCommand implements Command {
    private final String lineName;
    private final String stationNameToAppend;
    private final String prevStationName;
    private final Integer time;
    private final Subway subway;

    public AppendCommand(String lineName, String stationNameToAppend, String prevStationName, Integer time, Subway subway) {
        this.lineName = lineName;
        this.stationNameToAppend = stationNameToAppend;
        this.prevStationName = prevStationName;
        this.time = time;
        this.subway = subway;
    }

    @Override
    public void execute() {
        subway.append(lineName, stationNameToAppend, prevStationName, time);
    }
}
