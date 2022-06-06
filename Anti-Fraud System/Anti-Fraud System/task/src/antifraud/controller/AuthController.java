package antifraud.controller;

import antifraud.dto.DeleteUserResponse;
import antifraud.dto.LockUnlockReq;
import antifraud.dto.LockUnlockResp;
import antifraud.dto.UserRoleReq;
import antifraud.entity.User;
import antifraud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/auth/user")
    public ResponseEntity<User> registerNewUser(@RequestBody @Valid User user) {
        return new ResponseEntity<>(userService.registerNewUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/api/auth/list")
    public ResponseEntity<List<User>> getUsersInfo() {
        return new ResponseEntity<>(userService.getUsersInfo(), HttpStatus.OK);
    }

    @DeleteMapping("/api/auth/user/{username}")
    public ResponseEntity<DeleteUserResponse> deleteUser(@PathVariable(value = "username") String username) {
        return new ResponseEntity<>(userService.deleteUser(username), HttpStatus.OK);
    }

    @PutMapping("/api/auth/access")
    public ResponseEntity<LockUnlockResp> setLockUnlockUser(@RequestBody LockUnlockReq request) {
        return new ResponseEntity<>(userService.setLockUnlockUser(request), HttpStatus.OK);
    }

    @PutMapping("/api/auth/role")
    public ResponseEntity<User> setRoleUser(@RequestBody UserRoleReq request) {
        return new ResponseEntity<>(userService.setUserRole(request), HttpStatus.OK);
    }
}
