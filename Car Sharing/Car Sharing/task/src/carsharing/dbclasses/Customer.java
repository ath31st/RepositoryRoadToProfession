package carsharing.dbclasses;

public class Customer {
    private final int id;
    private final String name;
    private final int rented_car_id;

    public Customer(int id, String name, int rented_car_id) {
        this.id = id;
        this.name = name;
        this.rented_car_id = rented_car_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRented_car_id() {
        return rented_car_id;
    }

    @Override
    public String toString() {
        return name;
    }
}
