package fr.uge.jee.ugeoverflow.authentication;

import fr.uge.jee.ugeoverflow.security.CustomAuthenticationProvider;
import fr.uge.jee.ugeoverflow.user.Role;
import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {
    private AuthenticationService authenticationService;
    private UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    @GetMapping("/logout")
    public String processLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        if (authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/profile")
    public String homePage(Model model) {
        try {
            User user = this.authenticationService.getLoggedUser();
            model.addAttribute("loggedUser", user);
            return "redirect:/users/homepage/questions";
            //return "home-page";
        } catch (AuthenticationException exception) {
            return "login-form";
        }
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        User user = this.authenticationService.getLoggedUser();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "user-information";
    }

    @PostMapping("/settings/password")
    public String processPasswordUpdate(@RequestParam(name = "password") String password, Model model) {
        User user = this.authenticationService.getLoggedUser();
        this.authenticationService.updatePassword(user, password);
        model.addAttribute("updatePasswordSuccess", "Successful updated password");
        return "user-information";
    }

    @PostMapping("/settings/email")
    public String processEmailUpdate(@RequestParam(name = "email") String email, Model model) {
        User user = this.authenticationService.getLoggedUser();
        if (this.userService.isEmailAlreadyUse(email)) {
            model.addAttribute("updateEmailError", "Email is already used");
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            return "user-information";
        }
        this.authenticationService.updateEmail(user, email);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", email);
        model.addAttribute("updateEmailSuccess", "Successfully updated email");
        return "user-information";
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
        if (this.userService.isUsernameAlreadyUse(user.getUsername())) {
            model.addAttribute("registrationError", "This username is already used");
            return "registration-form";
        }
        if (this.userService.isEmailAlreadyUse(user.getEmail())) {
            model.addAttribute("registrationError", "This email is already used");
            return "registration-form";
        }
        user.setConfidenceScore(Collections.emptySet());
        user.setFollowedUsers(Collections.emptySet());
        user.setRole(Role.AUTHENTIFIED);
        this.authenticationService.saveUser(user);
        model.addAttribute("user", user);
        return "user-registration";
    }
}
