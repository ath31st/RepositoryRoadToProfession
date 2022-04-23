package carsharing.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Database implements CompanyDAO {
    static final String JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:file:./src/carsharing/db/";
    private final Connection connection;
    private static Database db = null;
    private static final String SQL_GET_ALL = "SELECT * FROM COMPANY;";
    private static final String SQL_GET_ALL_CUSTOMERS = "SELECT * FROM CUSTOMER;";
    private static final String SQL_GET_ONE = "SELECT * FROM COMPANY WHERE NAME = ?;";
    private static final String SQL_GET_ALL_CARS = "SELECT * FROM CAR WHERE COMPANY_ID = ?;";
    private static final String SQL_ADD_ONE = "INSERT INTO COMPANY (NAME) VALUES (?);";
    private static final String SQL_ADD_ONE_CUSTOMER = "INSERT INTO CUSTOMER (NAME) VALUES (?);";
    private static final String SQL_ADD_ONE_CAR = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?,?);";

    private Database(String dbName) {
        DB_URL = DB_URL + dbName;
        connection = getConnection();
        addCompanyTable();
        addCarTable();
        addCustomerTable();
    }

    private Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DB_URL);
            con.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Failed to get connection with db, check the url" + e.getMessage());
        }
        return con;
    }

    private void addCompanyTable() {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS COMPANY (\n"
                    + "     ID INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,\n"
                    + "     NAME VARCHAR(24) UNIQUE NOT NULL"
                    + ");";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    private void addCarTable() {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS CAR (\n"
                    + "     ID INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,\n"
                    + "     NAME VARCHAR(24) UNIQUE NOT NULL,\n"
                    + "     COMPANY_ID INTEGER NOT NULL,\n"
                    + "     CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID)\n"
                    + "     REFERENCES COMPANY (ID)"
                    + ");";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    private void addCustomerTable() {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS CUSTOMER (\n"
                    + "     ID INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,\n"
                    + "     NAME VARCHAR(50) UNIQUE NOT NULL,\n"
                    + "     RENTED_CAR_ID INTEGER,\n"
                    + "     CONSTRAINT FK_CAR FOREIGN KEY (RENTED_CAR_ID)\n"
                    + "     REFERENCES CAR (ID)"
                    + ");";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void insertNewCompany(String companyName) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_ONE)) {
            statement.setString(1, companyName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void insertNewCar(String carName, int companyId) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_ONE_CAR)) {
            statement.setString(1, carName);
            statement.setInt(2,companyId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void insertNewCustomer(String customerName) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_ONE_CUSTOMER)) {
            statement.setString(1, customerName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public static Database createDB(String dbName) {
        if (db == null) {
            db = new Database(dbName);
        }
        return db;
    }

    public void closeDB() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void dropCompanyTable() {
        try (Statement statement = connection.createStatement()) {
            String ifExistSqlQueue = "DROP TABLE IF EXISTS COMPANY;";
            statement.executeUpdate(ifExistSqlQueue);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> result = new ArrayList<>();
        try (ResultSet resultSet = connection.prepareStatement(SQL_GET_ALL).executeQuery()) {
            while (resultSet.next()) {
                result.add(new Company(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public List<Customer> getAllCustomers() {
        List<Customer> result = new ArrayList<>();
        try (ResultSet resultSet = connection.prepareStatement(SQL_GET_ALL_CUSTOMERS).executeQuery()) {
            while (resultSet.next()) {
                result.add(new Customer(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Car> getAllCars(int companyId) {
        List<Car> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_CARS)) {
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new Car(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Company findCompanyByName(String companyName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ONE)) {
            preparedStatement.setString(1, companyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            Company company = new Company();
            while (resultSet.next()) {
                company.setId(resultSet.getInt("id"));
                company.setCompanyName(resultSet.getString("name"));
            }
            return company;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}