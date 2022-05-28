package account.controller;

import account.entites.User;
import account.service.RoleService;
import account.service.UserService;
import account.util.RoleChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class ServiceFuncController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PutMapping("/user/role")
    public ResponseEntity<User> changeUserRole(@RequestBody RoleChangeRequest roleChangeRequest) {
        return roleService.changeUserRole(roleChangeRequest);
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String email) {
       return userService.deleteUser(email);
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUsersInfo() {
        return userService.getUsersInfo();
    }
}
