package client;

import com.google.gson.annotations.Expose;

public class GsonClientObject {
    @Expose
    private String type;
    @Expose
    private String key;
    @Expose
    private String value;


    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    public GsonClientObject(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }
}
