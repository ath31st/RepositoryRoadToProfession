package carsharing.db;

public class Car {
    private String carName;
    private int id;

    public Car(int id, String carName) {
        this.carName = carName;
        this.id = id;
    }

    public Car() {
    }

    public String getCarName() {
        return carName;
    }

    public int getId() {
        return id;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setId(int id) {
        this.id = id;
    }
}
