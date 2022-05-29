package account.util;

import org.springframework.stereotype.Component;

@Component
public class EmployeePaymentResponse {
    private String name;
    private String lastname;
    private String  period;
    private String salary;

    public EmployeePaymentResponse() {
    }

    public EmployeePaymentResponse(String name, String lastname, String period, String salary) {
        this.name = name;
        this.lastname = lastname;
        this.period = period;
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPeriod(String  period) {
        this.period = period;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPeriod() {
        return period;
    }

    public String getSalary() {
        return salary;
    }
}
