package antifraud.entity;

import javax.persistence.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String number;
    private long allowedAmountLimit = 200;
    private long manualAmountLimit = 1500;

    public Card() {
    }

    public Card(String number, long allowedAmountLimit, long manualAmountLimit) {
        this.number = number;
        this.allowedAmountLimit = allowedAmountLimit;
        this.manualAmountLimit = manualAmountLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAllowedAmountLimit() {
        return allowedAmountLimit;
    }

    public void setAllowedAmountLimit(long allowedAmountLimit) {
        this.allowedAmountLimit = allowedAmountLimit;
    }

    public long getManualAmountLimit() {
        return manualAmountLimit;
    }

    public void setManualAmountLimit(long manualAmountLimit) {
        this.manualAmountLimit = manualAmountLimit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
