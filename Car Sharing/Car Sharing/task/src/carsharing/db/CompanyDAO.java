package carsharing.db;

import java.util.List;

public interface CompanyDAO {
    List<Company> getAllCompanies();
    List<Car> getAllCars();

    public void addCompany(String companyName);

    void addCar(String carName);

}