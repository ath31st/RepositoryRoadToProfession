package metro.database;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private final String lineName;
    private final List<Station> startStationsList;
    private final List<Station> endStationsList;

    public Line(String lineName) {
        this.lineName = lineName;
        startStationsList = new ArrayList<>();
        endStationsList = new ArrayList<>();
    }

    public void addStartStation(Station station) {
        startStationsList.add(station);
    }

    public void addEndStation(Station station) {
        endStationsList.add(station);
    }

    public boolean append(String stationNameToAppend, String prevStationName, Integer time) {
        Station prevStation = Station.checkInstance(prevStationName, lineName);
        if (prevStation == null) {
            return false;
        }

        Station stationToAppend = Station.getInstance(stationNameToAppend, lineName, null);
        endStationsList.remove(prevStation);
        endStationsList.add(stationToAppend);
        prevStation.addNext(stationToAppend);
        prevStation.setTime(time);
        stationToAppend.addPrevious(prevStation);
        return true;
    }

    public boolean addHead(String stationNameToAdd, String nextStationName, Integer time) {
        Station nextStation = Station.checkInstance(nextStationName, lineName);
        if (nextStation == null) {
            return false;
        }

        Station stationToAdd = Station.getInstance(stationNameToAdd, lineName, time);
        startStationsList.remove(nextStation);
        startStationsList.add(stationToAdd);
        nextStation.addPrevious(stationToAdd);
        stationToAdd.addNext(nextStation);
        return true;
    }

    public boolean remove(String stationName) {
        boolean removed = false;
        for (Station startStation : startStationsList) {
            removed = removed || remove(startStation, stationName);
        }
        return removed;
    }

    private boolean remove(Station currentStation, String stationNameToRemove) {
        if (currentStation.getStationName().equals(stationNameToRemove)) {
            remove(currentStation);
            return true;
        }

        for (Station next : currentStation.getNextList()) {
            if (remove(next, stationNameToRemove)) {
                return true;
            }
        }
        return false;
    }

    private void remove(Station stationToRemove) {
        if (stationToRemove.getPrevList() == null) {
            removeFirst(stationToRemove);
            return;
        }
        if (stationToRemove.getNextList() == null) {
            removeLast(stationToRemove);
            return;
        }

        for (Station next : stationToRemove.getNextList()) {
            next.setPrevList(stationToRemove.getPrevList());
        }

        for (Station prev : stationToRemove.getPrevList()) {
            prev.setNextList(stationToRemove.getNextList());
        }

        if (stationToRemove.getTransferList() != null) {
            for (Transfer transfer : stationToRemove.getTransferList()) {
                transfer.getStation().deleteTransfer(stationToRemove);
            }
        }

        Station.deleteStation(stationToRemove);
    }

    private void removeFirst(Station stationToRemove) {
        startStationsList.remove(stationToRemove);

        if (stationToRemove.getNextList() == null) {
            endStationsList.remove(stationToRemove);
        } else {
            startStationsList.addAll(stationToRemove.getNextList());
            for (Station next : stationToRemove.getNextList()) {
                next.setPrevList(new ArrayList<>());
            }
        }

        Station.deleteStation(stationToRemove);
    }

    private void removeLast(Station stationToRemove) {
        endStationsList.remove(stationToRemove);

        if (stationToRemove.getPrevList() == null) {
            startStationsList.remove(stationToRemove);
        } else {
            endStationsList.addAll(stationToRemove.getPrevList());
            for (Station prev : stationToRemove.getPrevList()) {
                prev.setNextList(new ArrayList<>());
            }
        }
    }

    // TODO: 13-02-2022 does not work for looped lines
    public void output() {
        for (Station s : startStationsList) {
            System.out.println(s);
            for (Station n : s.getNextList()) {
                print(n);
            }
            System.out.println("----------------");
            System.out.println("----------------");
        }
    }

    private void print(Station station) {
        if (startStationsList.contains(station)) {
            return;
        }
        System.out.println(station);
        for (Station s : station.getNextList()) {
            print(s);
        }
    }
}
