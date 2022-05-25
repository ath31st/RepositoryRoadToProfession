package account;

import account.entites.Role;
import account.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private List<String> breachedPasswords;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        if (userRepository.findUserByEmailIgnoreCase(user.getEmail()).isEmpty()) {
            user.grantAuthority(Role.ROLE_USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        return user;
    }

    public ResponseEntity changePassword(String newPassword, User authUser) {
        checkValidPassword(newPassword);
        checkDifferencePasswords(newPassword, authUser.getPassword());
        User tmpUser = userRepository.findUserByEmailIgnoreCase(authUser.getEmail()).get();
        tmpUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(tmpUser);
        return ResponseEntity.ok()
                .header("email", tmpUser.getEmail())
                .header("status", "The password has been updated successfully")
                .build();
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password length must be 12 chars minimum!");
        }
        if (breachedPasswords.contains(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
    }

    private void checkDifferencePasswords(String newPassword, String oldPassword) {
        if (passwordEncoder.matches(newPassword, oldPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }
    }
}
