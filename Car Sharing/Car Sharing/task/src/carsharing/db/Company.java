package carsharing.db;

public class Company {
    private final String companyName;
    private final int id;

    Company (int id, String companyName) {
        this.companyName = companyName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }
}