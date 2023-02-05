package fr.uge.jee.ugeoverflow.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/register")
    public String userForm(User user) {
        return "user-form";
    }

    @PostMapping("/register")
    public String processForm(@ModelAttribute(name="user") @Valid User user,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            return "user-form";
        }
        user.setConfidenceScore(0L);
        user.setFollowedUsers(Collections.emptySet());
        user.setRole(Role.AUTHENTIFIED);
        this.userRepository.save(user);
        User savedUser = this.userRepository.findUserByUsername(user.getUsername());
        model.addAttribute("user", savedUser);

        return "user-registration";
    }
}
