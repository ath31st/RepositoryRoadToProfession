package carsharing.dbclasses;

public class CreateTable {

    static String tableOfCompanies = "CREATE TABLE IF NOT EXISTS company (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(30) NOT NULL UNIQUE" +
            ");";

    static String tableOfCars = "CREATE TABLE IF NOT EXISTS car (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(20) NOT NULL UNIQUE," +
            "company_id INT NOT NULL," +
            "CONSTRAINT fk_company FOREIGN KEY (company_id) " +
            "REFERENCES company(id)" +
            ");";

    static String tableOfCustomers = "CREATE TABLE IF NOT EXISTS customer (" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(30) NOT NULL UNIQUE, " +
            "rented_car_id INT DEFAULT NULL, " +
            "CONSTRAINT fk_car FOREIGN KEY (rented_car_id) " +
            "REFERENCES car(id)" +
            ");";

}
