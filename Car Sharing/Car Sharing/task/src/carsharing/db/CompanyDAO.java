package carsharing.db;

import java.util.List;
import java.util.Optional;

public interface CompanyDAO {
    List<Company> getAllCompanies();
    List<Car> getAllCars(int companyId);

    Company findCompanyByName(String companyName);

}