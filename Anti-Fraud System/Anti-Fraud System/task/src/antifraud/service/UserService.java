package antifraud.service;

import antifraud.dto.DeleteUserResponse;
import antifraud.dto.LockUnlockReq;
import antifraud.dto.StatusResp;
import antifraud.dto.UserRoleReq;
import antifraud.entity.User;
import antifraud.repository.UserRepository;
import antifraud.util.Operation;
import antifraud.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        checkExistingUser(user);
        if (userRepository.count() == 0) {
            user.grantAuthority(Role.ADMINISTRATOR);
        } else {
            user.setAccountNonLocked(false);
            user.grantAuthority(Role.MERCHANT);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public List<User> getUsersInfo() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

    public DeleteUserResponse deleteUser(String username) {
        User user = retrieveUserFromDb(username);
        userRepository.delete(user);
        return new DeleteUserResponse(user.getUsername(), "Deleted successfully!");
    }

    public StatusResp setLockUnlockUser(LockUnlockReq request) {
        User user = retrieveUserFromDb(request.getUsername());

        checkExistingOperation(request.getOperation());

        if (request.getOperation().equals(Operation.LOCK.name())) {
            user.setAccountNonLocked(false);
        } else if (request.getOperation().equals(Operation.UNLOCK.name())) {
            user.setAccountNonLocked(true);
        }
        userRepository.save(user);
        return new StatusResp(String.format("User %s %sed!", user.getUsername(), request.getOperation().toLowerCase()));
    }

    public User setUserRole(UserRoleReq request) {
        User user = retrieveUserFromDb(request.getUsername());

        checkExistingRole(request.getRole());
        checkInterchangeabilityRoles(user.getRole(), request.getRole());

        user.grantAuthority(Role.valueOf(request.getRole()));
        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsernameIgnoreCase(username);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", username));
        }
    }

    private void checkExistingUser(User user) {
        if (userRepository.findUserByUsernameIgnoreCase(user.getUsername()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User exist!");
    }

    private void checkExistingOperation(String operation) {
        if (Arrays.stream(Operation.values()).noneMatch(o -> o.name().equals(operation)))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found!");
    }

    private void checkExistingRole(String role) {
        if (Arrays.stream(Role.values()).noneMatch(r -> r.name().equals(role)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found!");
    }

    private void checkInterchangeabilityRoles(Role userRole, String grantRole) {
        if (userRole.name().equals(grantRole)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has that role!");
        } else if (grantRole.equals(Role.ADMINISTRATOR.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only one administrator here!");
        }

    }

    private User retrieveUserFromDb(String username) {
        return userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    }
}
