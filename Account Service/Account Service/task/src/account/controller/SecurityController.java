package account.controller;

import account.entites.Event;
import account.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SecurityController {
    @Autowired
    SecurityService securityService;

    @GetMapping("/security/events")
    public ResponseEntity<List<Event>> getEvents() {
        return securityService.getEvents();
    }
}
