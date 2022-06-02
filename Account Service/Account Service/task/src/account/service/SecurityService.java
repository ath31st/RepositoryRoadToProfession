package account.service;

import account.entites.Event;
import account.entites.User;
import account.repository.EventRepository;
import account.util.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SecurityService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private FailedLoginService failedLoginService;
    @Autowired
    private UserService userService;

    public ResponseEntity<List<Event>> getEvents() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().iterator().forEachRemaining(events::add);
        return ResponseEntity.ok().body(events);
    }

    public void createNewUserEvent(User user) {
        Event event = new Event();
        event.setDate(LocalDateTime.now());
        event.setAction(Action.CREATE_USER);
        event.setSubject("Anonymous");
        event.setObject(user.getEmail());
        event.setPath("/api/auth/signup");
        eventRepository.save(event);
    }

    public void createDeleteUserEvent(User user) {
        Event event = new Event();
        event.setDate(LocalDateTime.now());
        event.setAction(Action.DELETE_USER);
        event.setSubject(getCurrentUserName());
        event.setObject(user.getEmail());
        event.setPath("/api/admin/user");
        eventRepository.save(event);
    }

    public void createChangePasswordEvent(User user) {
        Event event = new Event();
        event.setDate(LocalDateTime.now());
        event.setAction(Action.CHANGE_PASSWORD);
        event.setSubject(user.getEmail());
        event.setObject(user.getEmail());
        event.setPath("/api/auth/changepass");
        eventRepository.save(event);
    }

    public void createGrantRoleEvent(User user, String roleFromReq) {
        Event event = new Event();
        event.setDate(LocalDateTime.now());
        event.setAction(Action.GRANT_ROLE);
        event.setSubject(getCurrentUserName());
        event.setObject(String.format("Grant role %s to %s", roleFromReq, user.getEmail()));
        event.setPath("/api/admin/user/role");
        eventRepository.save(event);
    }

    public void createRemoveRoleEvent(User user, String roleFromReq) {
        Event event = new Event();
        event.setDate(LocalDateTime.now());
        event.setAction(Action.REMOVE_ROLE);
        event.setSubject(getCurrentUserName());
        event.setObject(String.format("Remove role %s from %s", roleFromReq, user.getEmail()));
        event.setPath("/api/admin/user/role");
        eventRepository.save(event);
    }

    public void createUnlockUserEvent(User user) {
        Event event = new Event();
        event.setDate(LocalDateTime.now());
        event.setAction(Action.UNLOCK_USER);
        event.setSubject(getCurrentUserName());
        event.setObject("Unlock user " + user.getEmail());
        event.setPath("/api/admin/user/access");
        eventRepository.save(event);
    }

    public void createManualLockUserEvent(User user) {
        Event event = new Event();
        event.setDate(LocalDateTime.now());
        event.setAction(Action.LOCK_USER);
        event.setSubject(getCurrentUserName());
        event.setObject("Lock user " + user.getEmail());
        event.setPath("/api/admin/user/access");
        eventRepository.save(event);
    }

    public void createLockUserEvent(HttpServletRequest request) {
        Event event = new Event();
        event.setDate(LocalDateTime.now());
        event.setAction(Action.LOCK_USER);
        event.setSubject(getFailedLogin(request));
        event.setObject("Lock user " + getFailedLogin(request));
        event.setPath(request.getRequestURI()); // the endpoint where the event occurred
        eventRepository.save(event);
    }

    public void createBruteForceEvent(HttpServletRequest request) {
        Event event = new Event();
        event.setDate(LocalDateTime.now());
        event.setAction(Action.BRUTE_FORCE);
        event.setSubject(getFailedLogin(request));
        event.setObject(request.getRequestURI()); // the endpoint where the event occurred
        event.setPath(request.getRequestURI()); // the endpoint where the event occurred
        eventRepository.save(event);
    }

    public void createAccessDeniedEvent(HttpServletRequest request) {
        Event event = new Event();
        event.setDate(LocalDateTime.now());
        event.setAction(Action.ACCESS_DENIED);
        event.setSubject(getCurrentUserName());
        event.setObject(request.getRequestURI()); // the endpoint where the event occurred
        event.setPath(request.getRequestURI()); // the endpoint where the event occurred
        eventRepository.save(event);
    }

    public void createLoginFailedEvent(HttpServletRequest request) {
        int count = failedLoginService.getCountFailedLogin(getFailedLogin(request));
        if (count >= 5) {
            createBruteForceEvent(request);
            createLockUserEvent(request);
            userService.automaticLockUser(getFailedLogin(request));
        } else {
            failedLoginService.failedLoginUpdate(getFailedLogin(request));

            Event event = new Event();
            event.setDate(LocalDateTime.now());
            event.setAction(Action.LOGIN_FAILED);
            event.setSubject(getFailedLogin(request));
            event.setObject(request.getRequestURI()); // the endpoint where the event occurred
            event.setPath(request.getRequestURI()); // the endpoint where the event occurred
            eventRepository.save(event);
        }
    }

    private String getFailedLogin(HttpServletRequest request) {
        String failedLogin = request.getHeader("Authorization").replaceAll("Basic ", "");
        String decodedLogin = new String(Base64.getDecoder().decode(failedLogin.getBytes(StandardCharsets.UTF_8)));
        decodedLogin = decodedLogin.substring(0, decodedLogin.indexOf(":"));
        return decodedLogin;
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            currentUserName = user.getEmail();
        }
        return currentUserName;
    }

}
