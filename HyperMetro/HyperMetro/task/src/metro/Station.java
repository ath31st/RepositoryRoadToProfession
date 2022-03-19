package metro;

public class Station {
    private String stationNumber;
    private String stationName;

    public Station(String stationNumber, String stationName) {
        this.stationNumber = stationNumber;
        this.stationName = stationName;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public String getStationName() {
        return stationName;
    }
}
