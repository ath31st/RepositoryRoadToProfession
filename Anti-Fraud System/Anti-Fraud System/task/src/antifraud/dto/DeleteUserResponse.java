package antifraud.dto;

public class DeleteUserResponse {
    private String username;
    private String status;

    public DeleteUserResponse() {
    }

    public DeleteUserResponse(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
