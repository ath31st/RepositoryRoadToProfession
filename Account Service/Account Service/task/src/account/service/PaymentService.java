package account.service;

import account.entites.Payment;
import account.exceptionhandler.DuplicatePaymentException;
import account.exceptionhandler.EmoloyeeNotFoundException;
import account.exceptionhandler.SalaryException;
import account.repository.PaymentRepository;
import account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Map<String, String>> addPaymentsIntoDb(List<Payment> paymentList) {
        checkValidatePayment(paymentList);
        paymentList.forEach(paymentRepository::save);
        return ResponseEntity.ok().body(Map.of("status", "Added successfully!"));
    }

    private List<Payment> getAllPaymentsByEmployee(String emailEmployee) {
        return paymentRepository.findPaymentByEmployeeIgnoreCase(emailEmployee);
    }

    private void checkValidatePayment(List<Payment> paymentList) {
        for (Payment payment : paymentList) {
            if (userRepository.findUserByEmailIgnoreCase(payment.getEmployee()).isEmpty()) {
                throw new EmoloyeeNotFoundException();
            }
            if (payment.getSalary() < 0) {
                throw new SalaryException();
            }
            List<Payment> payments = getAllPaymentsByEmployee(payment.getEmployee());
            if (payments.stream().anyMatch(payment::equals)) {
                throw new DuplicatePaymentException();
            }

        }
    }
}
