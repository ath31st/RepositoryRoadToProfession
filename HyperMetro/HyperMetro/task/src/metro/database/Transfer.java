package metro.database;

public class Transfer {
    private final Station station;

    public Transfer(Station station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)",
                station.getStationName(), station.getLineName());
    }

    public Station getStation() {
        return station;
    }
}
