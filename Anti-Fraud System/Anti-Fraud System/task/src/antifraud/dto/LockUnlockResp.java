package antifraud.dto;

public class LockUnlockResp {
    private String status;

    public LockUnlockResp(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
