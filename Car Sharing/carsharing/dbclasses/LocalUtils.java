package carsharing.dbclasses;

public class LocalUtils {
    private static final String DB_URL = "jdbc:h2:";

    private static final String localPathDB_URL = "./src/carsharing/db/";
    private static String localDBName = "testDB";

    /**
     * checking if the program was started with the database name
     * default name "testDB"
     *
     * @param args a String array from "main(String[] args)"
     * @return the database name
     */
    public static String runParam(String[] args) {
        if (args.length > 0 && "-databaseFileName".equals(args[0])) {
            localDBName = args[1];
        }
        return String.format("%s%s%s", DB_URL, localPathDB_URL, localDBName);
    }
}
