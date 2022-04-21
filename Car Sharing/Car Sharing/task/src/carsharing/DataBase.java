package carsharing;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class DataBase {
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

    public static void createDB(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            DataBase.registerJDBSDriver();
            conn = DataBase.createConnection(DB_URL + pathDB(args));
            conn.setAutoCommit(true);
            stmt = conn.createStatement();

            String sql = "CREATE TABLE company" +
                    "(id INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";
            stmt.execute("DROP TABLE IF EXISTS company");
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
