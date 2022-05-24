package account.controller;

import account.User;
import account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
       return ResponseEntity.ok().body(userService.registerNewUser(user));
    }

    @PostMapping("/api/auth/changepass")
    public void changeUserPass() {

    }
}
