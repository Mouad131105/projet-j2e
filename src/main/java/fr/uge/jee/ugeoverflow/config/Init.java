package fr.uge.jee.ugeoverflow.config;

import fr.uge.jee.ugeoverflow.user.Role;
import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class Init {
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Value("${admin.email}")
    private String email;

    public Init(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        User admin = new User(
                this.username,
                this.passwordEncoder.encode(this.password),
                this.email,
                Role.ADMIN,
                Collections.emptySet()
        );
        this.userService.save(admin);
    }


}
