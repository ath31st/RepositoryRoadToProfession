package carsharing.dbclasses;

import java.sql.*;

import static carsharing.dbclasses.CreateTable.*;

public class DBClass {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    Connection conn = null;
    Statement stmt = null;

    public DBClass(String dbName) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(dbName);
            conn.setAutoCommit(true);
            stmt = conn.createStatement();

            updateColumnOfCompany();
            updateColumnOfCar();
            updateColumnOfCustomer();

            stmt.executeUpdate(tableOfCompanies);
            stmt.executeUpdate(tableOfCars);
            stmt.executeUpdate(tableOfCustomers);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateColumnOfCompany() {
        try {
            String sql = "ALTER TABLE IF EXISTS company ALTER COLUMN id RESTART WITH 1;";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateColumnOfCar() {
        try {
            String sql = "ALTER TABLE IF EXISTS car ALTER COLUMN id RESTART WITH 1;";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateColumnOfCustomer() {
        try {
            String sql = "ALTER TABLE IF EXISTS customer ALTER COLUMN id RESTART WITH 1;";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCompany(String newCompany) {
        try {
            String sql = "INSERT INTO company (name) " + "VALUES ('" + newCompany + "')";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCar(String newCar, int company_id) {
        try {
            String sql = "INSERT INTO car (name, company_id) " +
                    "VALUES ('" + newCar + "', " + company_id + ")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCustomer(String newCustomer) {
        try {
            String sql = "INSERT INTO customer (name) " +
                    "VALUES ('" + newCustomer + "')";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRentedCarIdNull(int id) {
        try {
            String sql = "UPDATE customer SET rented_car_id = NULL " +
                    "WHERE id = " + id;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRentedCar(int customerId, int carId) {
        try {
            String sql = "UPDATE customer SET rented_car_id = " + carId +
                    " WHERE id = " + customerId;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeDB() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}