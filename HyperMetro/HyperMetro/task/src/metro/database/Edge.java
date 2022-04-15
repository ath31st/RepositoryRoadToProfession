package metro.database;

public class Edge {
    private final Station fromStation;
    private final Station toStation;
    private final Integer cost;

    public Edge(Station fromStation, Station toStation, Integer cost) {
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.cost = cost;
    }

    public Station getToStation() {
        return toStation;
    }

    public Integer getCost() {
        return cost;
    }
}