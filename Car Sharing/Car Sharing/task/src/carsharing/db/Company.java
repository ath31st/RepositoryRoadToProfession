package carsharing.db;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String companyName;
    private int id;

    private List<Car> cars = new ArrayList<>();

    Company(int id, String companyName) {
        this.companyName = companyName;
        this.id = id;
    }

    public Company() {
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}