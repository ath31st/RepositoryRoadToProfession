package util;

import java.util.HashMap;
import java.util.Map;

public class Delete implements Command{
    private Map<Integer, String> db;
    private int index;

    public Delete(HashMap<Integer,String> db, int index) {
        this.db = db;
        this.index = index;
    }

    @Override
    public void execute() {
        db.remove(index);
    }
}
