package account.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceFuncController {
    @PutMapping("/api/admin/user/role")
    public void changeUserRole(){

    }
    @DeleteMapping("/api/admin/user")
    public void deleteUser(){

    }
    @GetMapping("/api/admin/user")
    public void getUserInfo(){

    }
}