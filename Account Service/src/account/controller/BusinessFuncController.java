package account.controller;

import account.entites.Payment;
import account.service.PaymentService;
import account.service.UserService;
import account.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class BusinessFuncController {

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentService paymentService;


    @GetMapping("/empl/payment")
    public ResponseEntity getPaymentForPeriod(@RequestParam(value = "period",required = false) String period, @AuthenticationPrincipal User user) {
        return paymentService.getPaymentForPeriod(period, user);
    }

    @PostMapping("/acct/payments")
    public ResponseEntity uploadsPayrolls(@RequestBody List<Payment> paymentList) {
        return paymentService.addPaymentsIntoDb(paymentList);
    }

    @PutMapping("/acct/payments")
    public ResponseEntity updatePaymentinfo(@RequestBody Payment payment) {
        return paymentService.putPaymentChanges(payment);
    }
}
