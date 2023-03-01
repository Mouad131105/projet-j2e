package fr.uge.jee.ugeoverflow.user;

import fr.uge.jee.ugeoverflow.publishing.question.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/registration")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/profile")
    public String processProfile(@RequestParam("username") String username,
                                 Model model) {

        User user = this.userService.findUserByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);

            List<Question> questions = this.userService.getAllQuestionFromUser(user.getUsername());
            model.addAttribute("questions", questions);

            return "user-profile";
        }

        model.addAttribute("selectedUserError", "User " + username + " cannot be found");
        return "home-page";
    }

    @GetMapping("/register")
    public String userForm(User user) {
        return "registration-form";
    }

    @PostMapping("/register")
    public String processForm(@ModelAttribute(name="user") @Valid User user,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            return "registration-form";
        }
        user.setConfidenceScore(0L);
        user.setFollowedUsers(Collections.emptySet());
        user.setRole(Role.AUTHENTIFIED);
        this.userService.save(user);
        User savedUser = this.userService.findUserByUsername(user.getUsername());
        model.addAttribute("user", savedUser);

        return "user-registration";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam(name = "username") @NotEmpty String username,
                              @RequestParam(name = "password") @NotEmpty String password,
                               Model model) {

        List<User> users = this.userService.getAllUsers();
        if(!users.isEmpty()){
            model.addAttribute("allUsers",users);
        }

        User user = this.userService.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("loggedUser", user);
            return "home-page";
        }

        model.addAttribute("loginError", "Username or password is incorrect");

        return "login-form";
    }
}
