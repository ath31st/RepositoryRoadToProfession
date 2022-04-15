package metro.database;

import java.util.*;

public class DijkstrasShortestPath {
    // Small epsilon value to compare double values
    private static final double EPS = 1e-6;
    private final Map<Station, List<Edge>> graph;
    private final Comparator<Node> comparator = (node1, node2) -> {
        if (Math.abs(node1.value - node2.value) < EPS) {
            return 0;
        }
        return (node1.value - node2.value) > 0 ? 1 : -1;
    };

    public DijkstrasShortestPath(Map<Station, List<Edge>> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        this.graph = graph;
    }

    public List<Station> reconstructPath(Station fromStation, Station toStation) {
        Map<Station, Station> prev = new HashMap<>();
        int n = graph.size();
        Map<Station, Double> dist = new HashMap<>();
        for (Station s : graph.keySet()) {
            dist.put(s, Double.POSITIVE_INFINITY);
        }

        dijkstra(fromStation, dist, prev);
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

    public void dijkstra(Station fromStation, Map<Station, Double> dist, Map<Station, Station> prev) {
        dist.put(fromStation, 0.0);

        // Keep a priority queue of the next most promising node to visit
        PriorityQueue<Node> pq = new PriorityQueue<>(comparator);
        pq.offer(new Node(fromStation, 0));

        Map<Station, Boolean> visited = new HashMap<>();
        for (Station s : graph.keySet()) {
            visited.put(s, false);
        }
        visited.replace(fromStation, true);

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            visited.put(node.station, true);

            // Ignore the outdated pairs where we already found a better path routing through other nodes
            if (dist.get(node.station) < node.value) {
                continue;
            }

            List<Edge> edges = graph.get(node.station);
            for (Edge edge : edges) {
                if (visited.get(edge.getToStation())) {
                    continue;
                }

                // Relax edge by updating minimum cost if possible
                double newDist = dist.get(node.station) + edge.getCost();
                if (newDist < dist.get(edge.getToStation())) {
                    prev.put(edge.getToStation(), node.station);
                    dist.put(edge.getToStation(), newDist);
                    pq.offer(new Node(edge.getToStation(), dist.get(edge.getToStation())));
                }
            }
        }
    }

    // Node class to track the nodes to visit
    static class Node {
        Station station;
        double value;

        public Node(Station station, double value) {
            this.station = station;
            this.value = value;
        }
    }
}
