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
import java.util.Set;

@Controller
@RequestMapping("/registration")
public class UserController {
    private UserService userService;
    private QuestionService questionService;

    public UserController(UserService userService, QuestionService questionService) {
        this.userService = userService;
        this.questionService = questionService;
    }

    @PostMapping("/follow")
    public String processFollow(@RequestParam("userFollow") String userFollow,
                                @RequestParam("loggedUser") String loggedUser,
                                Model model) {

        User user = this.userService.findUserByUsername(userFollow);
        User currentUser = this.userService.findUserByUsername(loggedUser);
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("loggedUser", currentUser);
            Set<User> followedUsers = currentUser.getFollowedUsers();
            if(followedUsers.contains(user)){
                followedUsers.remove(user);
                currentUser.setFollowedUsers(followedUsers);
                this.userService.save(currentUser);
                model.addAttribute("isFollow", false);
            }else{
                followedUsers.add(user);
                currentUser.setFollowedUsers(followedUsers);
                this.userService.save(currentUser);
                model.addAttribute("isFollow", true);
            }
            List<Question> questions = this.userService.getAllQuestionFromUser(user.getUsername());
            model.addAttribute("questions", questions);
        }
        return "user-profile";
    }
    @GetMapping("/profile/{username}")
    public String getProfile(@PathVariable("username") String username,
                                 @RequestParam(name = "loggedUser") String loggedUser,
                                 Model model) {

        User user = this.userService.findUserByUsername(username);
        User currentUser = this.userService.findUserByUsername(loggedUser);
        if (user != null && currentUser != null) {
            model.addAttribute("user", user);
            model.addAttribute("loggedUser", currentUser);
            if(currentUser.getFollowedUsers().contains(user)){
                model.addAttribute("isFollow", true);
            }else{
                model.addAttribute("isFollow", false);
            }
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
            Page<Question> questions = questionService.findAll(0, 5);
            model.addAttribute("listQuestions", questions.getContent());
            model.addAttribute("pages", new int[questions.getTotalPages()]);
            model.addAttribute("currentPage", 0);
            return "home-page";
        }

        model.addAttribute("loginError", "Username or password is incorrect");

        return "login-form";
    }
}
