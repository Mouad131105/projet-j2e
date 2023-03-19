package fr.uge.jee.ugeoverflow.authentication;

import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserService;
import fr.uge.jee.ugeoverflow.utils.Utils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // https://docs.spring.io/spring-security/site/docs/4.0.2.RELEASE/reference/htmlsingle/#obtaining-information-about-the-current-user
    public User getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            String username = usernamePasswordAuthenticationToken.getPrincipal().toString();
            User user;
            if (username.matches(Utils.EMAIL_REGEX)) {
                user = this.userService.findUserWithEmail(username);
            }
            else {
                user = this.userService.findUserByUsername(username);
            }
            return user;
        }
        throw new AuthenticationException("Current user isn't authenticate");
    }

    public void updatePassword(User user, String password) {
        user.setPassword(this.passwordEncoder.encode(password));
        this.userService.save(user);
    }

    public void updateEmail(User user, String email) {
        user.setEmail(email);
        this.userService.save(user);
    }

    public void saveUser(User user) {
        String rawPassword = user.getPassword();
        String encodedPassword = this.passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        this.userService.save(user);
    }
}
