package carsharing.db;

import java.util.List;

public interface CompanyDAO {
    List<Company> getAllCompanies();
    public void addCompany(String companyName);

}