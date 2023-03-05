package fr.uge.jee.ugeoverflow.controller;

import fr.uge.jee.ugeoverflow.entities.Question;
import fr.uge.jee.ugeoverflow.entities.Role;
import fr.uge.jee.ugeoverflow.entities.User;
import fr.uge.jee.ugeoverflow.service.QuestionService;
import fr.uge.jee.ugeoverflow.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/registration")
public class UserController {
    private UserService userService;
    private QuestionService questionService;

    public UserController(UserService userService, QuestionService questionService) {
        this.userService = userService;
        this.questionService = questionService;
    }

    @GetMapping("/profile/{username}")
    public String getProfile(@PathVariable("username") String username,
                                 @RequestParam(name = "loggedUsername") String loggedUsername,
                                 Model model) {
        model.addAttribute("username",username);
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

    /*@PostMapping("/profile")
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
    }*/

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
            Page<Question> questions = questionService.findAll(0, 10);
            model.addAttribute("listQuestions", questions.getContent());
            model.addAttribute("pages", new int[questions.getTotalPages()]);
            model.addAttribute("currentPage", 0);
            return "home-page";
        }

        model.addAttribute("loginError", "Username or password is incorrect");

        return "login-form";
    }
}
