package metro.database;

import java.util.*;

public class BreadthFirstSearch {
    private final Map<Station, List<Edge>> graph;

    public BreadthFirstSearch(Map<Station, List<Edge>> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        this.graph = graph;
    }

    public List<Station> reconstructPath(Station fromStation, Station toStation) {
        Map<Station, Station> prev = new HashMap<>();
        bfs(fromStation, prev);
        List<Station> path = new ArrayList<>();

        Station at = toStation;
        path.add(at);
        while (prev.get(at) != null) {
            path.add(prev.get(at));
            at = prev.get(at);
        }

        Collections.reverse(path);
        if (path.get(0) == fromStation) {
            return path;
        }
        path.clear();
        return path;
    }

    private void bfs(Station fromStation, Map<Station, Station> prev) {
        Map<Station, Boolean> visited = new HashMap<>();
        for (Station s : graph.keySet()) {
            visited.put(s, false);
        }
        Queue<Station> queue = new ArrayDeque<>();

        queue.offer(fromStation);
        visited.replace(fromStation, true);

        while (!queue.isEmpty()) {
            Station station = queue.poll();
            List<Edge> edges = graph.get(station);

            // Loop through all edges
            for (Edge edge : edges) {
                if (!visited.get(edge.getToStation())) {
                    visited.put(edge.getToStation(), true);
                    prev.put(edge.getToStation(), station);
                    queue.offer(edge.getToStation());
                }
            }
        }
    }
}

