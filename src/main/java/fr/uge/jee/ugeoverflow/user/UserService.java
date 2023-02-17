package fr.uge.jee.ugeoverflow.user;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(Objects.requireNonNull(username));
    }

    public boolean existsUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    public String getPassword(String username) {
        return this.userRepository.getUserByPassword(username);
    }
}
