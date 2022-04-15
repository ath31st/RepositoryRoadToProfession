package metro.database;

import java.util.HashMap;
import java.util.Map;

public class Subway {
    private final String inputFile;
    private final Map<String, Line> allLines;

    public Subway(String inputFile) {
        this.inputFile = inputFile;
        this.allLines = new HashMap<>();
    }

    public boolean populateMap() {
        return ReadSubwayData.readFromFile(inputFile, allLines);
    }

    public void fastestRoute(String lineName1, String stationName1, String lineName2, String stationName2) {
        Station fromStation = Station.checkInstance(stationName1, lineName1);
        Station toStation = Station.checkInstance(stationName2, lineName2);
        if (fromStation == null || toStation == null) {
            System.out.println("Invalid command. Wrong station or line names.");
            return;
        }

        Graph graph = new Graph();
        graph.findRouteDijkstra(fromStation, toStation);
    }

    public void route(String lineName1, String stationName1, String lineName2, String stationName2) {
        Station fromStation = Station.checkInstance(stationName1, lineName1);
        Station toStation = Station.checkInstance(stationName2, lineName2);
        if (fromStation == null || toStation == null) {
            System.out.println("Invalid command. Wrong station or line names.");
            return;
        }

        Graph graph = new Graph();
        graph.findRouteBFS(fromStation, toStation);
    }

    public void connect(String lineName1, String stationName1, String lineName2, String stationName2) {
        Station station1 = Station.checkInstance(stationName1, lineName1);
        Station station2 = Station.checkInstance(stationName2, lineName2);
        if (station1 == null || station2 == null) {
            System.out.println("Invalid command. Wrong station or line names.");
            return;
        }

        station1.addTransfer(new Transfer(station2));
        station2.addTransfer(new Transfer(station1));
    }

    public void append(String lineName, String stationNameToAppend, String prevStationName, Integer time) {
        Line line = allLines.get(lineName);

        if (line != null) {
            boolean append = line.append(stationNameToAppend, prevStationName, time);
            if (!append) {
                System.out.println("Invalid command. Wrong station name.");
            }
        } else {
            System.out.println("Invalid command. Wrong line name.");
        }
    }

    public void addHead(String lineName, String stationNameToAdd, String nextStationName, Integer time) {
        Line line = allLines.get(lineName);

        if (line != null) {
            boolean added = line.addHead(stationNameToAdd, nextStationName, time);
            if (!added) {
                System.out.println("Invalid command. Wrong station name.");
            }
        } else {
            System.out.println("Invalid command. Wrong line name.");
        }
    }

    public void remove(String lineName, String stationName) {
        Line line = allLines.get(lineName);

        if (line != null) {
            boolean removed = line.remove(stationName);
            if (!removed) {
                System.out.println("Invalid command. Wrong station name.");
            }
        } else {
            System.out.println("Invalid command. Wrong line name.");
        }
    }

    // does not work for looped lines
    public void output(String lineName) {
        Line line = allLines.get(lineName);

        if (line != null) {
            line.output();
        } else {
            System.out.println("Invalid command. Wrong line name.");
        }
    }
}
