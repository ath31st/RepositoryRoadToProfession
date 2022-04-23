package carsharing.dbclasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JavaLists {
    DBClass dbClass;
    List<Company> companies;
    List<Car> cars;
    List<Customer> customers;

    public JavaLists(DBClass dbClass) {
        this.dbClass = dbClass;
        companies = new ArrayList<>();
        cars = new ArrayList<>();
        customers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM company;";
            ResultSet rsComp = dbClass.stmt.executeQuery(sql);
            while (rsComp.next()) {
                int id = rsComp.getInt("id");
                String name = rsComp.getNString("name");
                companies.add(new Company(id, name));
            }

            sql = "SELECT * FROM car;";
            ResultSet rsCar = dbClass.stmt.executeQuery(sql);
            while (rsCar.next()) {
                int id = rsCar.getInt("id");
                String name = rsCar.getNString("name");
                int company_id = rsCar.getInt("company_id");
                cars.add(new Car(id, name, company_id));
            }

            sql = "SELECT * FROM customer;";
            ResultSet rsCustomer = dbClass.stmt.executeQuery(sql);
            while (rsCustomer.next()) {
                int id = rsCustomer.getInt("id");
                String name = rsCustomer.getNString("name");
                int rented_car_id = rsCustomer.getInt("rented_car_id");
                customers.add(new Customer(id, name, rented_car_id));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Company> getAllCompanies() {
        return companies;
    }

    public List<Car> getAllCars() {
        return cars;
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }
}
