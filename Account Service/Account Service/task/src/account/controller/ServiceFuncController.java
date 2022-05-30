package account.controller;

import account.dto.DeleteUserResponse;
import account.dto.UserStatusChangeRequest;
import account.dto.UserStatusChangeResponse;
import account.entites.User;
import account.service.RoleService;
import account.service.UserService;
import account.dto.RoleChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<User> changeUserRole(@RequestBody @Valid RoleChangeRequest roleChangeRequest) {
        return roleService.changeUserRole(roleChangeRequest);
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<DeleteUserResponse> deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUsersInfo() {
        return userService.getUsersInfo();
    }

    @PutMapping("/user/access")
    public ResponseEntity<UserStatusChangeResponse> changeUserStatus(@RequestBody UserStatusChangeRequest request) {
        return userService.changeUserStatus(request);
    }
}
