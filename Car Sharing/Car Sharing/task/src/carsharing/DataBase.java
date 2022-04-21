package carsharing;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static String DB_URL = "jdbc:h2:~/carsharing/db/";

    //  Database credentials
//    private static final String USER = "sa";
//    private static final String PASS = "";

    public static void registerJDBSDriver() throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
    }

    public static Connection createConnection(String db_url) throws SQLException {
        return DriverManager.getConnection(db_url);
    }

    public static String pathDB(String[] args) {
        String dbName = "carsharing";
        if (args.length == 2 & args[0].equalsIgnoreCase("-databaseFileName")) {
            dbName = args[1];
        }
        return dbName;
    }
}
