package account.repository;

import account.entites.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
    List<Payment> findPaymentByEmployeeIgnoreCase(String email);
}
