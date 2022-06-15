package account.dto;

public class UserStatusChangeResponse {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserStatusChangeResponse() {
    }

    public UserStatusChangeResponse(String status) {
        this.status =  status;
    }
}
