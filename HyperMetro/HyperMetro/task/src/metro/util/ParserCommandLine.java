package metro.util;

public class ParserCommandLine {
    private static String path;

    public static String getPath() {
        return path;
    }

    public static void parse(String[] args) {
        path = args[0].isEmpty() ? "ERROR" : args[0];
    }
}
