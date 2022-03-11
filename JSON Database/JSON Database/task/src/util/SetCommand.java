package util;

import client.GsonClientObject;
import server.GsonServerObject;

import java.util.HashMap;
import java.util.Map;

public class SetCommand implements Command {
    private Map<String, String> db;
    private GsonClientObject gsonClientObject;
    private GsonServerObject gsonServerObject;

    public SetCommand(HashMap<String, String> db, GsonClientObject gsonClientObject, GsonServerObject gsonServerObject) {
        this.db = db;
        this.gsonClientObject = gsonClientObject;
        this.gsonServerObject = gsonServerObject;
    }

    @Override
    public void execute() {
        db.put(gsonClientObject.getKey(), gsonClientObject.getValue());
        gsonServerObject.setResponse("OK");
    }
}
