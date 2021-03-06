package account.service;

import account.dto.ChangeUserPasswordResponse;
import account.dto.DeleteUserResponse;
import account.dto.UserStatusChangeRequest;
import account.dto.UserStatusChangeResponse;
import account.repository.UserRepository;
import account.util.Operation;
import account.util.Role;
import account.entites.User;
import account.exceptionhandler.exception.BreachedPasswordException;
import account.exceptionhandler.exception.InvalidLengthPasswordException;
import account.exceptionhandler.exception.RepetitivePasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private List<String> breachedPasswords;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FailedLoginService failedLoginService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email.toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    }

    public User registerNewUser(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        checkValidPassword(user.getPassword());
        if (userRepository.count() == 0) {
            user.grantAuthority(Role.ROLE_ADMINISTRATOR);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            securityService.createNewUserEvent(user);
        } else if (userRepository.findUserByEmailIgnoreCase(user.getEmail()).isEmpty()) {
            user.grantAuthority(Role.ROLE_USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            securityService.createNewUserEvent(user);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        return user;
    }

    public ResponseEntity<ChangeUserPasswordResponse> changePassword(String newPassword, User authUser) {
        checkValidPassword(newPassword);
        checkDifferencePasswords(newPassword, authUser.getPassword());
        User tmpUser = userRepository.findUserByEmailIgnoreCase(authUser.getEmail()).get();
        tmpUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(tmpUser);
        securityService.createChangePasswordEvent(tmpUser);
        return ResponseEntity.ok().body(new ChangeUserPasswordResponse(authUser.getEmail(), "The password has been updated successfully"));
    }

    public ResponseEntity<List<User>> getUsersInfo() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(users::add);
        return ResponseEntity.ok().body(users);
    }

    public ResponseEntity<DeleteUserResponse> deleteUser(String email) {
        User user = userRepository.findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user.getRoles().contains(Role.ROLE_ADMINISTRATOR))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        userRepository.delete(user);
        securityService.createDeleteUserEvent(user);
        return ResponseEntity.ok().body(new DeleteUserResponse(user.getEmail(), "Deleted successfully!"));
    }

    public ResponseEntity<UserStatusChangeResponse> changeUserStatus(UserStatusChangeRequest request) {
        User user = userRepository.findUserByEmailIgnoreCase(request.getUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        if (user.getRoles().contains(Role.ROLE_ADMINISTRATOR))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't lock the ADMINISTRATOR!");
        if (request.getOperation().equals(Operation.LOCK.name())) {
            user.setAccountNonLocked(false);
            userRepository.save(user);
            securityService.createManualLockUserEvent(user);
            return ResponseEntity.ok().body(new UserStatusChangeResponse("User " + request.getUser().toLowerCase() + " locked!"));
        } else if (request.getOperation().equals(Operation.UNLOCK.name())) {
            user.setAccountNonLocked(true);
            userRepository.save(user);
            failedLoginService.resetCounterFailedLogin(user.getEmail());
            securityService.createUnlockUserEvent(user);
            return ResponseEntity.ok().body(new UserStatusChangeResponse("User " + request.getUser().toLowerCase() + " unlocked!"));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found!");
        }
    }

    public void automaticLockUser(String email) {
        User user = userRepository.findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user.getRoles().contains(Role.ROLE_ADMINISTRATOR))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't lock the ADMINISTRATOR!");
        user.setAccountNonLocked(false);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmailIgnoreCase(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", email));
        }
    }

    private void checkValidPassword(String password) {
        if (password == null || password.length() < 12) {
            throw new InvalidLengthPasswordException();
        }
        if (breachedPasswords.contains(password)) {
            throw new BreachedPasswordException();
        }
    }

    private void checkDifferencePasswords(String newPassword, String oldPassword) {
        if (passwordEncoder.matches(newPassword, oldPassword)) {
            throw new RepetitivePasswordException();
        }
    }
}
