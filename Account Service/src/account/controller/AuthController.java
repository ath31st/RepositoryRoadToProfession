package account.controller;

import account.dto.ChangeUserPasswordResponse;
import account.entites.User;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok().body(userService.registerNewUser(user));
    }

    @PostMapping("/changepass")
    public ResponseEntity<ChangeUserPasswordResponse> changeUserPass(@RequestBody Map<String,String> newPassword, @AuthenticationPrincipal User user) {
        return userService.changePassword(newPassword.get("new_password"),user);
    }
}
