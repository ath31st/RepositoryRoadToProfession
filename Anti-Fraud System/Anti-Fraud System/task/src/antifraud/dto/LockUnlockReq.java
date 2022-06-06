package antifraud.dto;


public class LockUnlockReq {
    private String username;
    private String operation;

    public LockUnlockReq() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
