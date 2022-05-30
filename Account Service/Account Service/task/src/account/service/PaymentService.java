package account.service;

import account.entites.Payment;
import account.entites.User;
import account.exceptionhandler.exception.*;
import account.repository.PaymentRepository;
import account.repository.UserRepository;
import account.dto.EmployeePaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public ResponseEntity<Map<String, String>> putPaymentChanges(Payment payment) {
        checkExistsEmployee(payment);
        checkCorrectSalary(payment);
        Payment paymentFromDb = paymentRepository
                .findPaymentByEmployeeIgnoreCaseAndPeriod(payment.getEmployee(), payment.getPeriod())
                .orElseThrow(EmployeeNotFoundException::new);
        paymentFromDb.setSalary(payment.getSalary());
        paymentRepository.save(paymentFromDb);
        return ResponseEntity.ok().body(Map.of("status", "Updated successfully!"));
    }

    public ResponseEntity getPaymentForPeriod(String period, User user) {
        if (period == null) {
            List<Payment> payments = paymentRepository.findPaymentByEmployeeIgnoreCase(user.getEmail());
            payments.sort(Comparator.comparing(Payment::getPeriod).reversed());
            List<EmployeePaymentResponse> responses = new ArrayList<>();
            for (Payment payment : payments) {
                responses.add(createResponse(payment, user));
            }
            return ResponseEntity.ok().body(responses);
        } else {
            Payment payment = paymentRepository.findPaymentByEmployeeIgnoreCaseAndPeriod(user.getEmail(), getYearMonthFromString(period))
                    .orElseThrow(PaymentNotFoundException::new);
            EmployeePaymentResponse response = createResponse(payment, user);
            return ResponseEntity.ok().body(response);
        }
    }

    private EmployeePaymentResponse createResponse(Payment payment, User user) {
        DateTimeFormatter english = DateTimeFormatter.ofPattern("MMMM-yyyy", Locale.ENGLISH);
        EmployeePaymentResponse response = new EmployeePaymentResponse();
        response.setName(user.getUsername());
        response.setLastname(user.getLastname());
        response.setPeriod(payment.getPeriod().format(english));
        response.setSalary(String.format("%d dollar(s) %d cent(s)", payment.getSalary() / 100, payment.getSalary() % 100));
        return response;
    }


    private List<Payment> getAllPaymentsByEmployee(String emailEmployee) {
        return paymentRepository.findPaymentByEmployeeIgnoreCase(emailEmployee);
    }

    private void checkValidatePayment(List<Payment> paymentList) {
        for (Payment payment : paymentList) {
            checkCorrectSalary(payment);
            checkExistsEmployee(payment);
            checkDuplicatePayments(payment);
        }
    }

    private void checkCorrectSalary(Payment payment) {
        if (payment.getSalary() < 0) {
            throw new SalaryException();
        }
    }

    private void checkExistsEmployee(Payment payment) {
        if (userRepository.findUserByEmailIgnoreCase(payment.getEmployee()).isEmpty()) {
            throw new EmployeeNotFoundException();
        }
    }

    private void checkDuplicatePayments(Payment payment) {
        List<Payment> payments = getAllPaymentsByEmployee(payment.getEmployee());
        if (payments.stream().anyMatch(payment::equals)) {
            throw new DuplicatePaymentException();
        }
    }

    private YearMonth getYearMonthFromString(String period) {
        YearMonth per = null;
        try {
            per = YearMonth.parse(period, DateTimeFormatter.ofPattern("MM-yyyy"));
        } catch (DateTimeException e) {
            throw new WrongPeriodException();
        }
        return per;
    }
}
