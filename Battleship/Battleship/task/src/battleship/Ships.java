package battleship;

public enum Ships {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private String nameShip;
    private int cells;

    Ships(String nameShip, int cells) {
        this.nameShip = nameShip;
        this.cells = cells;
    }

    public String getNameShip() {
        return nameShip;
    }

    public int getCells() {
        return cells;
    }
}
