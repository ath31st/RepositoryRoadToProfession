package account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
   private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) throws ResponseStatusException {
        return userRepository.findByName(username.toLowerCase());
        // .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public User registerNewUser(User user) {
        if (userRepository.findByName(user.getName()) == null) {
//            user.setRole("ROLE_USER");
//            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists!");
        return user;
    }
}
