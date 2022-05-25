package account.controller;

import account.entites.User;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok().body(userService.registerNewUser(user));
    }

    @PostMapping("/api/auth/changepass")
    public ResponseEntity changeUserPass(@RequestBody Map<String,String> newPassword, @AuthenticationPrincipal User user) {
        return userService.changePassword(newPassword.get("new_password"),user);
    }
}
