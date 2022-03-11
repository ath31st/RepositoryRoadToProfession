package server;

import com.google.gson.annotations.Expose;

public class GsonServerObject {
    @Expose
    private String response;
    @Expose
    private String reason;
    @Expose
    private String value;

    public void setResponse(String response) {
        this.response = response;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public GsonServerObject() {

    }

    public GsonServerObject(String response, String reason, String value) {
        this.response = response;
        this.reason = reason;
        this.value = value;
    }
}
