package carsharing.db;

public class Car {
    private final String carName;
    private final int id;

    public Car(int id, String carName) {
        this.carName = carName;
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public int getId() {
        return id;
    }
}
