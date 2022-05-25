package account.controller;

import account.entites.User;
import account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok().body(userService.registerNewUser(user));
    }

    @PostMapping("/api/auth/changepass")
    public ResponseEntity changeUserPass(@RequestBody String new_password, @AuthenticationPrincipal User user) {
       return userService.changePassword(new_password,user);
    }
}
