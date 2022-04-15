package metro.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Station {
    private static final Map<String, Station> allStations = new HashMap<>();
    private final String stationName;
    private final String lineName;
    private final List<Transfer> transferList;
    private Integer time;
    private List<Station> prevList;
    private List<Station> nextList;

    public Station(String stationName, String lineName, Integer time) {
        this.stationName = stationName;
        this.lineName = lineName;
        this.time = time;
        this.prevList = new ArrayList<>();
        this.nextList = new ArrayList<>();
        this.transferList = new ArrayList<>();
    }

    // key for allStations map will be "stationName^lineName"
    public static Station getInstance(String stationName, String lineName, Integer time) {
        String key = stationName + "^" + lineName;
        Station station = allStations.get(key);

        if (station == null) {
            station = new Station(stationName, lineName, time);
            allStations.put(key, station);
        }
        return station;
    }

    // key for allStations map will be "stationName^lineName"
    public static Station checkInstance(String stationName, String lineName) {
        String key = stationName + "^" + lineName;
        return allStations.get(key);
    }

    public static Map<String, Station> getAllStations() {
        return allStations;
    }

    public static void deleteStation(String stationName, String lineName) {
        String key = stationName + "^" + lineName;
        allStations.remove(key);
    }

    public static void deleteStation(Station station) {
        deleteStation(station.getStationName(), station.getLineName());
    }

    public void addPrevious(Station station) {
        prevList.add(station);
    }

    public void addNext(Station station) {
        nextList.add(station);
    }

    public List<Station> getPrevList() {
        return prevList;
    }

    public void setPrevList(List<Station> prevList) {
        this.prevList = prevList;
    }

    public List<Station> getNextList() {
        return nextList;
    }

    public void setNextList(List<Station> nextList) {
        this.nextList = nextList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.stationName);
        for (Transfer t : transferList) {
            sb.append(" - ").append(t);
        }
        return sb.toString();
    }

    public String getStationName() {
        return stationName;
    }

    public String getLineName() {
        return lineName;
    }

    public List<Transfer> getTransferList() {
        return transferList;
    }

    public void addTransfer(Transfer transfer) {
        transferList.add(transfer);
    }

    public void deleteTransfer(Station station) {
        transferList.removeIf(transfer -> transfer.getStation() == station);
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
