package engine.businesslayer.services;

import engine.businesslayer.User;
import engine.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        } else throw new UsernameNotFoundException(user.getUsername());
        return user;
    }

}
