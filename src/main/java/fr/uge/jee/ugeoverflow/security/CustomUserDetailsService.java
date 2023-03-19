package fr.uge.jee.ugeoverflow.security;

import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final String EMAIL_REGEX = "^\\S+@\\S+\\.\\S+";
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if (username.matches(EMAIL_REGEX)) {
            user = this.userRepository.findByEmail(username);
            if (user == null) {
                String message = String.format("User with email %s does not exist", username);
                throw new UsernameNotFoundException(message);
            }
        }
        else {
            user = this.userRepository.findUserByUsername(username);
            if (user == null) {
                String message = String.format("User with username %s does not exist", username);
                throw new UsernameNotFoundException(message);
            }
        }
        return new CustomUserDetails(user);
    }
}


