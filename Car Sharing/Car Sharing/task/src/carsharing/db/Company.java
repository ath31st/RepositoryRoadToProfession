package carsharing.db;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String companyName;
    private int id;

    Company(int id, String companyName) {
        this.companyName = companyName;
        this.id = id;
    }

    public Company() {
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setId(int id) {
        this.id = id;
    }
}