package util;

import java.util.HashMap;
import java.util.Map;

public class Get implements Command {
    private Map<Integer, String> db;
    private int index;
    public static String result = "";

    public Get(HashMap<Integer, String> db, int index) {
        this.db = db;
        this.index = index;
    }

    @Override
    public void execute() {
        result = db.getOrDefault(index, "");
    }
}
