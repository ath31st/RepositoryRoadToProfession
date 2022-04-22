package carsharing.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements CompanyDAO{
    static final String JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:file:./src/carsharing/db/";
    private final Connection connection;
    private static Database db = null;
    private static final String SQL_GET_ALL = "SELECT * FROM COMPANY;";
    private static final String SQL_ADD_ONE = "INSERT INTO COMPANY (NAME) VALUES (?);";

    private Database (String dbName) {
        DB_URL = DB_URL + dbName;
        connection = getConnection();
        addCOMPANYTable();
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

    private void addCOMPANYTable() {
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

    private void insertNewCompany(String companyName) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_ONE)) {
            statement.setString(1, companyName);
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

    public void dropCOMPANYTable() {
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

    @Override
    public void addCompany(String companyName) {
        insertNewCompany(companyName);
    }
}