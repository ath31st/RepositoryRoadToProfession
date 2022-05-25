package account.controller;

import account.UserService;
import account.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
class BusinessFuncController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/empl/payment")
    public ResponseEntity<User> getPayment(@AuthenticationPrincipal User user) {
        User tmpUser = userService.findByEmail(user.getEmail());
        return new ResponseEntity<>(tmpUser, HttpStatus.OK);
    }

    @PostMapping("/api/acct/payments")
    public void uploadsPayrolls() {

    }

    @PutMapping("/api/acct/payments")
    public void updatePaymentinfo() {

    }
}
