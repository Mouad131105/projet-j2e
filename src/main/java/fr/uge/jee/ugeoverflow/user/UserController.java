package fr.uge.jee.ugeoverflow.user;

import fr.uge.jee.ugeoverflow.authentication.AuthenticationService;
import fr.uge.jee.ugeoverflow.note.Note;
import fr.uge.jee.ugeoverflow.note.NoteService;
import fr.uge.jee.ugeoverflow.publishing.question.Question;
import fr.uge.jee.ugeoverflow.publishing.question.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private QuestionService questionService;
    private AuthenticationService authenticationService;
    private NoteService noteService;

    public UserController(UserService userService, QuestionService questionService,
                          AuthenticationService authenticationService, NoteService noteService) {
        this.userService = userService;
        this.questionService = questionService;
        this.authenticationService = authenticationService;
        this.noteService = noteService;
    }

    @PostMapping("/note")
    public String processNote(@RequestParam("userFollow") String userFollow,
                              @RequestParam("selectedNote") Long selectedNote,
                              Model model) {
        User currentUser = this.authenticationService.getLoggedUser();
        Set<Note> notes = currentUser.getConfidenceScore();
        Note newNote = new Note();
        newNote.setReceiverUsername(userFollow);
        newNote.setScore(selectedNote);
        newNote.setAuthorUsername(currentUser.getUsername());
        Note note = this.noteService.save(newNote);
        notes.add(note);
        currentUser.setConfidenceScore(notes);
        this.userService.save(currentUser);

        return "redirect:/users/profile/" + userFollow;
    }


    @PostMapping("/follow")
    public String processFollow(@RequestParam("userFollow") String userFollow, Model model) {
        User user = this.userService.findUserByUsername(userFollow);
        User currentUser = this.authenticationService.getLoggedUser();
        if (user != null) {
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
        }
        return "redirect:/users/profile/" + userFollow;
    }

    @GetMapping("/profile/{username}")
    public String getProfile(@PathVariable("username") String username, Model model) {

        User user = this.userService.findUserByUsername(username);
        User currentUser = this.authenticationService.getLoggedUser();
        Set<User> followedUsers = currentUser.getFollowedUsers();
        Note userNotes = currentUser.getConfidenceScore().stream().filter(x -> x.getReceiverUsername().equals(user.getUsername())).findFirst().orElse(null);
        if (user != null && currentUser != null) {
            if(userNotes != null){
                model.addAttribute("note", userNotes.getScore());
            }else{
                model.addAttribute("note", null);
            }
            model.addAttribute("user", user);
            model.addAttribute("loggedUser", currentUser);

            if(followedUsers.contains(user)){
                model.addAttribute("isFollow", true);
            }else{
                model.addAttribute("isFollow", false);
            }

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
            List<Question> otherQuestions = new ArrayList<>(questionService.findAll());

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
        List<User> users = this.userService.getAllUsers();
        if(!users.isEmpty()){
            model.addAttribute("allUsers",users);
        }
        List<Question> sortedQuestions = questionService.getSortedQuestionsByFollowing(user);
        List<Question> otherQuestions = new ArrayList<>(questionService.findAll());

        otherQuestions.removeAll(sortedQuestions);

        sortedQuestions.addAll(otherQuestions);

        int pageSize = 5;
        List<List<Question>> pages = IntStream.range(0, (sortedQuestions.size() + pageSize - 1) / pageSize)
                .mapToObj(i -> sortedQuestions.subList(i * pageSize, Math.min((i + 1) * pageSize, sortedQuestions.size())))
                .collect(Collectors.toList());

        List<Question> firstPage = pages.get(0);
        Page<Question> questions = new PageImpl<>(firstPage, PageRequest.of(0, pageSize), sortedQuestions.size());

        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", 0);
        model.addAttribute("isTag", false);
        model.addAttribute("isSearch", false);
        return "home-page-questions";
    }

    @PostMapping("/homepage/users")
    public String homepageUsers(Model model){

        List<User> users = this.userService.getAllUsers();
        String loggedUser = authenticationService.getLoggedUser().getUsername();
        User user = users.stream().filter(x -> x.getUsername().equals(loggedUser)).findFirst().orElse(null);
        model.addAttribute("loggedUser", user);
        if(!users.isEmpty()){
            model.addAttribute("allUsers",users);
        }
        return "home-page-users";
    }

    @PostMapping("/homepage/tags")
    public String homepageTags(Model model){
        User user = this.authenticationService.getLoggedUser();
        model.addAttribute("loggedUser", user);
        return "home-page-tags";
    }
}
