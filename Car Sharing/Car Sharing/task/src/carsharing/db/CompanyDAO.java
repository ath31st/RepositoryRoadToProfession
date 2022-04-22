package carsharing.db;

import java.util.List;
import java.util.Optional;

public interface CompanyDAO {
    List<Company> getAllCompanies();
    List<Car> getAllCars();

    void addCompany(String companyName);

    void addCar(String carName);

    Company findCompanyByName(String companyName);

}