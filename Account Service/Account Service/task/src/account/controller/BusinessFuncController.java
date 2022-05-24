package account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class BusinessFuncController {
    @GetMapping("/api/empl/payment")
    public void getEmpoyeesPayrolls() {

    }

    @PostMapping("/api/acct/payments")
    public void uploadsPayrolls() {

    }

    @PutMapping("/api/acct/payments")
    public void updatePaymentinfo() {

    }
}
