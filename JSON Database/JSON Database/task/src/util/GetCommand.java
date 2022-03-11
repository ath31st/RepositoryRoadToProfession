package util;

import client.GsonClientObject;
import server.GsonServerObject;

import java.util.HashMap;
import java.util.Map;

public class GetCommand implements Command {
    private Map<String , String> db;
    private GsonClientObject gsonClientObject;
    private GsonServerObject gsonServerObject;

    public GetCommand(HashMap<String, String> db, GsonClientObject gsonClientObject, GsonServerObject gsonServerObject) {
        this.db = db;
        this.gsonClientObject = gsonClientObject;
        this.gsonServerObject = gsonServerObject;
    }

    @Override
    public void execute() {
        if (db.containsKey(gsonClientObject.getKey())) {
            gsonServerObject.setResponse("OK");
            gsonServerObject.setValue(db.get(gsonClientObject.getKey()));
        } else {
            gsonServerObject.setResponse("ERROR");
            gsonServerObject.setReason("No such key");
        }
    }
}
