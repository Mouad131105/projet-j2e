package fr.uge.jee.ugeoverflow.user;

import fr.uge.jee.ugeoverflow.authentication.AuthenticationService;
import fr.uge.jee.ugeoverflow.note.Note;
import fr.uge.jee.ugeoverflow.publishing.question.Question;
import fr.uge.jee.ugeoverflow.publishing.question.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private QuestionService questionService;
    private AuthenticationService authenticationService;

    public UserController(UserService userService, QuestionService questionService,
                          AuthenticationService authenticationService) {
        this.userService = userService;
        this.questionService = questionService;
        this.authenticationService = authenticationService;
    }

    /*@PostMapping("/note")
    public String processNote(@RequestParam("userFollow") String username,
                                @RequestParam("loggedUser") String loggedUser,
                              @RequestParam("loggedUser") Long selectedNote,
                                Model model) {
        User user = this.userService.findUserByUsername(username);
        User currentUser = this.userService.findUserByUsername(loggedUser);
        Set<Note> notes = user.getConfidenceScore();
        Note newNote = new Note();
        newNote.setReceiverUsername(username);
        newNote.setScore(selectedNote);
        newNote.setAuthor(currentUser);
        notes.add(newNote);
        user.setConfidenceScore(notes);
        this.userService.save(user);
    }*/


    @PostMapping("/follow")
    public String processFollow(@RequestParam("userFollow") String userFollow, Model model) {
        User user = this.userService.findUserByUsername(userFollow);
        User currentUser = this.authenticationService.getLoggedUser();
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("loggedUser", currentUser);
            Set<User> followedUsers = this.userService.findAllFollowedUsersFromUser(currentUser.getUsername());
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
    public String getProfile(@PathVariable("username") String username, Model model) {

        User user = this.userService.findUserByUsername(username);
        User currentUser = this.authenticationService.getLoggedUser();

        /*Note note = this.userService.findNoteFromReceiverAndAuthor(user.getUsername(), currentUser.getUsername());
        if(note != null){
            model.addAttribute("note", note.getScore());
        }else{
            model.addAttribute("note", null);
        }*/

        Set<User> followedUsers = this.userService.findAllFollowedUsersFromUser(currentUser.getUsername());

        if(followedUsers.contains(user)){
            model.addAttribute("isFollow", true);
        }else{
            model.addAttribute("isFollow", false);
        }

        System.out.println("after");
        System.out.println(followedUsers);
        if (user != null && currentUser != null) {
            model.addAttribute("user", user);
            model.addAttribute("loggedUser", currentUser);
            List<Question> questions = this.userService.getAllQuestionFromUser(user.getUsername());
            model.addAttribute("questions", questions);
            return "user-profile";
        }

        model.addAttribute("selectedUserError", "User " + username + " cannot be found");
        return "home-page-questions";
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
        //user.setConfidenceScore(Collections.emptySet());
        user.setFollowedUsers(Collections.emptySet());
        user.setRole(Role.AUTHENTIFIED);
        this.userService.save(user);
        //User savedUser = this.userService.findUserByUsername(user.getUsername());
        model.addAttribute("user", user);

        return "user-registration";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    /*
    @PostMapping("/login")
    public String processLogin(@RequestParam(name = "username") @NotEmpty String username,
                              @RequestParam(name = "password") @NotEmpty String password,
                               Model model) {

        User user = this.userService.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("loggedUser", user);
            Page<Question> questions = questionService.findAll(0, 5);
            model.addAttribute("listQuestions", questions.getContent());
            model.addAttribute("pages", new int[questions.getTotalPages()]);
            model.addAttribute("currentPage", 0);
            return "home-page-questions";
        }

        model.addAttribute("loginError", "Username or password is incorrect");

        return "login-form";
    }*/

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

            List<Question> sortedQuestions = questionService.getSortedQuestionsByFollowing(user);
            List<Question> otherQuestions = (List<Question>) questionService.findAll(0, 5);

            // Supprimez les questions déjà triées de la liste des autres questions
            otherQuestions.removeAll(sortedQuestions);

            // Ajoutez les autres questions après les questions triées
            sortedQuestions.addAll(otherQuestions);

            Page<Question> questions = new PageImpl<>(sortedQuestions, PageRequest.of(0, 5), sortedQuestions.size());
            model.addAttribute("listQuestions", questions.getContent());
            model.addAttribute("pages", new int[questions.getTotalPages()]);
            model.addAttribute("currentPage", 0);
            return "home-page";
        }

        model.addAttribute("loginError", "Username or password is incorrect");

        return "login-form";
    }
    @RequestMapping(value = "/homepage/questions", method = {RequestMethod.POST, RequestMethod.GET})
    public String homepageQuestions(Model model){
        User user = this.authenticationService.getLoggedUser();
        model.addAttribute("loggedUser", user);
        Page<Question> questions = questionService.findAll(0, 5);
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", 0);
        return "home-page-questions";
    }

    @PostMapping("/homepage/users")
    public String homepageUsers(@RequestParam(name = "loggedUser") String loggedUser,
                                   Model model){

        List<User> users = this.userService.getAllUsers();
        User user = users.stream().filter(x -> x.getUsername().equals(loggedUser)).findFirst().orElse(null);
        model.addAttribute("loggedUser", user);
        if(!users.isEmpty()){
            model.addAttribute("allUsers",users);
        }
        return "home-page-users";
    }

    @PostMapping("/homepage/tags")
    public String homepageTags(@RequestParam(name = "loggedUser") String loggedUser,
                               Model model){
        User user = this.userService.findUserByUsername(loggedUser);
        model.addAttribute("loggedUser", user);
        return "home-page-tags";
    }
}
