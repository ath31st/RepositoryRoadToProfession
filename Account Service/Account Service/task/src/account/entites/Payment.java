package account.entites;

import account.config.YearMonthDateAttributeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.Objects;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
    private String employee;
    @Column
    @Convert(converter = YearMonthDateAttributeConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yyyy")
    private YearMonth period;
    @Column
    private Long salary;


    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public YearMonth getPeriod() {
        return period;
    }

    public void setPeriod(YearMonth period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(employee, payment.employee) && Objects.equals(period, payment.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, period);
    }
}
