package fr.uge.jee.ugeoverflow.user;

import fr.uge.jee.ugeoverflow.authentication.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findOne")
    public ResponseEntity<User> findUserWithUsername(@RequestParam(name = "username") String username) {
        if (!this.userService.existsUsername(username)) {
            String message = String.format("User with username %s does not exist", username);
            return new ResponseEntity(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.userService.findUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(this.userService.findAllUsers(), HttpStatus.OK);
    }


}
