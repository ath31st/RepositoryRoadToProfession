package util;

import java.util.HashMap;
import java.util.Map;

public class Set implements Command {
    private Map<Integer, String> db;
    private int index;
    private String value;

    public Set(HashMap<Integer,String> db, int index, String value) {
        this.db = db;
        this.index = index;
        this.value = value;
    }

    @Override
    public void execute() {
        db.put(index,value);
    }
}
