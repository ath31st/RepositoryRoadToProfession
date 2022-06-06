package antifraud.service;

import antifraud.dto.DeleteUserResponse;
import antifraud.entity.User;
import antifraud.repository.UserRepository;
import antifraud.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        if (userRepository.findUserByUsernameIgnoreCase(user.getUsername()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User exist!");
//        user.setUsername(user.getUsername().toLowerCase());
        user.grantAuthority(Role.USER);
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
        User user = userRepository.findUserByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        userRepository.delete(user);
        return new DeleteUserResponse(user.getUsername(), "Deleted successfully!");
    }
}
