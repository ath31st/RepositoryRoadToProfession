package antifraud.dto;

public class ManualProcessingReq {
    private long transactionId;
    private String feedback;

    public ManualProcessingReq() {
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
