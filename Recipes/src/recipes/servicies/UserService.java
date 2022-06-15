package recipes.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.entites.User;
import recipes.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerNewUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setRole("ROLE_USER");
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return user;
    }

    public User findByUsername(String username) throws ResponseStatusException {
        return userRepository.findByUsername(username.toLowerCase());
    }


}
