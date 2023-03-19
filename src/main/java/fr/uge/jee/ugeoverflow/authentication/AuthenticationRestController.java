package fr.uge.jee.ugeoverflow.authentication;

import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserService;
import fr.uge.jee.ugeoverflow.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationRestController {
    private UserService userService;
    private AuthenticationService authenticationService;

    public AuthenticationRestController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/update/{username}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable String username,  @RequestParam(name = "password") String password) {
        if (!Pattern.matches(Utils.PASSWORD_REGEX, password) || this.userService.existsUsername(username)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = this.userService.findUserByUsername(username);
        this.authenticationService.updatePassword(user, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
