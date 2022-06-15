package server.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;

/**
 * Database record
 */
public class RecordInBase {

    @Expose
    private final String key;
    @Expose
    private final JsonElement value;

    public RecordInBase(String key, JsonElement value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }
}