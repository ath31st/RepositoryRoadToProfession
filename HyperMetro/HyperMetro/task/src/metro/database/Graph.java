package metro.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Station, List<Edge>> graph;

    private void formatPath(List<Station> path, boolean printDistance) {
        if (path == null) {
            System.out.println("No path possible.");
            return;
        }

        double distance = 0.0;
        Station prev = null;
        for (Station station : path) {
            if (printDistance && prev != null && prev.getLineName().equals(station.getLineName())) {
                Integer time = station.getPrevList().contains(prev) ? prev.getTime() : station.getTime();
                distance += time;
            }
            if (prev != null && !prev.getLineName().equals(station.getLineName())) {
                System.out.println("Transition to line " + station.getLineName());
                // as per current specifications, transfer between lines are 5 minutes
                if (printDistance) {
                    distance += 5;
                }
            }

            System.out.println(station.getStationName());
            prev = station;
        }
        if (printDistance) {
            System.out.println("Total: " + (int) distance + " minutes in the way");
        }
    }

    public void findRouteDijkstra(Station fromStation, Station toStation) {
        populateDijkstraGraph();
        DijkstrasShortestPath solver = new DijkstrasShortestPath(graph);

        List<Station> path = solver.reconstructPath(fromStation, toStation);
        formatPath(path, true);
    }

    private void populateDijkstraGraph() {
        graph = new HashMap<>();
        for (Station station : Station.getAllStations().values()) {
            graph.put(station, new ArrayList<>());

            if (!station.getTransferList().isEmpty()) {
                for (Transfer transfer : station.getTransferList()) {
                    if (station.getStationName().equals(transfer.getStation().getStationName())) {
                        // as per current specifications, transfer between lines are 5 minutes
                        addDirectedEdge(station, transfer.getStation(), 5);
                    } else {
                        // This case should not be possible as all transfer stations are between same stations on different lines
                        // Still, this is added to account for the edge case of user calling connect command and then calling route or fastest-route command

                        // The edge will have a weight of station.getTime() as it starts at station
                        addDirectedEdge(station, transfer.getStation(), station.getTime());
                    }
                }
            }

            if (!station.getPrevList().isEmpty()) {
                for (Station prev : station.getPrevList()) {
                    // The edge will have a weight of prev.getTime() as it starts at prev
                    addDirectedEdge(station, prev, prev.getTime());
                }
            }

            if (!station.getNextList().isEmpty()) {
                for (Station next : station.getNextList()) {
                    // The edge will have a weight of station.getTime() as it starts at station
                    addDirectedEdge(station, next, station.getTime());
                }
            }
        }
    }

    public void findRouteBFS(Station fromStation, Station toStation) {
        populateBFSGraph();
        BreadthFirstSearch solver = new BreadthFirstSearch(graph);

        List<Station> path = solver.reconstructPath(fromStation, toStation);
        formatPath(path, false);
    }

    public void populateBFSGraph() {
        graph = new HashMap<>();
        for (Station station : Station.getAllStations().values()) {
            graph.put(station, new ArrayList<>());

            if (!station.getTransferList().isEmpty()) {
                for (Transfer transfer : station.getTransferList()) {
                    if (station.getStationName().equals(transfer.getStation().getStationName())) {
                        // transfer between lines is assumed to have 0 cost
                        addDirectedEdge(station, transfer.getStation(), 0);
                    } else {
                        // This case should not be possible as all transfer stations are between same stations on different lines
                        // Still, this is added to account for the edge case of user calling connect command and then calling route or fastest-route command

                        // The edge will have a weight of 1 as all edges have same weight
                        addDirectedEdge(station, transfer.getStation(), 1);
                    }
                }
            }

            if (!station.getPrevList().isEmpty()) {
                for (Station prev : station.getPrevList()) {
                    // The edge will have a weight of 1 as all edges have same weight
                    addDirectedEdge(station, prev, 1);
                }
            }

            if (!station.getNextList().isEmpty()) {
                for (Station next : station.getNextList()) {
                    // The edge will have a weight of 1 as all edges have same weight
                    addDirectedEdge(station, next, 1);
                }
            }
        }
    }

    // Add a directed weighted edge from station u to station v with weight cost
    private void addDirectedEdge(Station u, Station v, Integer cost) {
        graph.get(u).add(new Edge(u, v, cost));
    }
}
