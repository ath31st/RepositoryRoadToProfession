package antifraud.util;

public enum Reason {
    IP("ip"),
    CARD_NUMBER("card-number"),
    REGION_CORRELATION("region-correlation"),
    IP_CORRELATION("ip-correlation"),
    AMOUNT("amount");


    private final String reason;

    public String getReason() {
        return reason;
    }

    Reason(String reason) {
        this.reason = reason;
    }
}
