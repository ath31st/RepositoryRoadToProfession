package carsharing;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class Application {
    public static void run(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        System.out.println(Arrays.toString(args));
        try {
            DataBase.registerJDBSDriver();
            conn = DataBase.createConnection("jdbc:h2:./src/carsharing/db/" + args[1]);
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
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}
