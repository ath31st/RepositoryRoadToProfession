package engine.presentation;

import engine.businesslayer.entities.User;
import engine.businesslayer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/test")
    public String test() {
        return "/test is accessed";
    }

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        userService.registerNewUser(user);
    }
}
