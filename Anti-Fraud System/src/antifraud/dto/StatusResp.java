package antifraud.dto;

public class StatusResp {
    private String status;

    public StatusResp(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
