package carsharing.db;

public class Customer {
    private String customerName;
    private int id;

    public Customer(int id, String customerName) {
        this.customerName = customerName;
        this.id = id;
    }

    public Customer() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getId() {
        return id;
    }
}
