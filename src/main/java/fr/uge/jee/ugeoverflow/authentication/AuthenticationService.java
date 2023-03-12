package fr.uge.jee.ugeoverflow.authentication;

import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    UserService userService;

    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    // https://docs.spring.io/spring-security/site/docs/4.0.2.RELEASE/reference/htmlsingle/#obtaining-information-about-the-current-user
    public User getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            String username = usernamePasswordAuthenticationToken.getPrincipal().toString();
            User user = this.userService.findUserByUsername(username);
            return user;
        }
        throw new AuthenticationException("Current user isn't authenticate");
    }
}
