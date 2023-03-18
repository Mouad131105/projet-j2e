package fr.uge.jee.ugeoverflow.publishing.question;

import fr.uge.jee.ugeoverflow.publishing.Tag;
import fr.uge.jee.ugeoverflow.publishing.comment.CommentQuestion;
import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.publishing.comment.CommentQuestionService;
import fr.uge.jee.ugeoverflow.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;
    private final CommentQuestionService commentQuestionService;

    public QuestionController(QuestionService questionService, UserService userService,
                              CommentQuestionService commentQuestionService) {
        this.questionService = questionService;
        this.userService = userService;
        this.commentQuestionService = commentQuestionService;
    }


    @PostMapping("/comment/question")
    public String processForm(@ModelAttribute(name = "commentQuestion") @Valid CommentQuestion commentQuestion,
                              BindingResult bindingResult,
                              Model model) {

        CommentQuestion newCommentQuestion = this.commentQuestionService.save(commentQuestion);
        Question question = newCommentQuestion.getParentQuestion();
        List<CommentQuestion> commentQuestions = this.commentQuestionService.findAllByParentQuestionId(question.getId());
        model.addAttribute("question", question);
        model.addAttribute("commentsQuestion", commentQuestions);
        model.addAttribute("loggedUser", newCommentQuestion.getAuthor());

        return "question-profile";
    }

    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable("id") String id,
                             @RequestParam(name = "loggedUser") String loggedUser,
                             CommentQuestion commentQuestion,
                             Model model) {

        Question question = this.questionService.findQuestionById(Long.valueOf(id));
        List<CommentQuestion> commentQuestions = this.commentQuestionService.findAllByParentQuestionId(question.getId());

        model.addAttribute("question", question);
        model.addAttribute("commentsQuestion", commentQuestions);
        model.addAttribute("loggedUser", loggedUser);

        return "question-profile";
    }

    @GetMapping("/create")
    public String questionForm(@RequestParam String username, Question question, Model model) {
        model.addAttribute("allTags", Arrays.asList(Tag.values()));

        User loggedUser = this.userService.findUserByUsername(username);
        if (loggedUser != null) {
            model.addAttribute("loggedUser", loggedUser);
            question.setAuthor(loggedUser);
            return "question-form";
        }
        model.addAttribute("selectedUserError", "User " + username + " cannot be found");
        return "question-form"; //normalement reviens sur la homepage car erreur sur l'utilisateur actuel
    }

    @PostMapping("/create")
    public String processForm(@ModelAttribute(name = "question") @Valid Question question,
                              @RequestParam(name = "loggedUser") String loggedUser,
                              CommentQuestion commentQuestion,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("allTags", Arrays.asList(Tag.values()));
            return "question-form";
        }

        Question createdQuestion = this.questionService.save(question);
        model.addAttribute("createdQuestion", createdQuestion);
        model.addAttribute("commentsQuestion", this.commentQuestionService.findAllByParentQuestionId(createdQuestion.getId()));
        model.addAttribute("loggedUser", loggedUser);
        return "question-profile";
    }

    /*@GetMapping("/questions")
    public String questions(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "loggedUser") String loggedUser) {
        List<User> users = this.userService.getAllUsers();
        if (!users.isEmpty()) {
            model.addAttribute("allUsers", users);
        }
        User user = this.userService.findUserByUsername(loggedUser);
        model.addAttribute("loggedUser", user);
        Page<Question> questions = questionService.findAll(page, 5);
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "home-page-questions";
    }*/
    @GetMapping("/questions")
    public String questions(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "loggedUser") String loggedUser) {
        List<User> users = this.userService.getAllUsers();
        if(!users.isEmpty()){
            model.addAttribute("allUsers",users);
        }
        User user = this.userService.findUserByUsername(loggedUser);
        model.addAttribute("loggedUser", user);

        List<Question> sortedQuestions = questionService.getSortedQuestionsByFollowing(user);
        List<Question> otherQuestions = (List<Question>) questionService.findAll(0, 5);

        // Supprimez les questions déjà triées de la liste des autres questions
        otherQuestions.removeAll(sortedQuestions);

        // Ajoutez les autres questions après les questions triées
        sortedQuestions.addAll(otherQuestions);

        Page<Question> questions = new PageImpl<>(sortedQuestions, PageRequest.of(page, 5), sortedQuestions.size());
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "home-page-questions";
    }

    /*@PostMapping("/search")
    public String searchQuestion(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                 @RequestParam(name = "loggedUser") String loggedUser){
        List<User> users = this.userService.getAllUsers();
        if(!users.isEmpty()){
            model.addAttribute("allUsers",users);
        }
        User user = this.userService.findUserByUsername(loggedUser);
        model.addAttribute("loggedUser", user);
        Page<Question> questions = questionService.findByTopicContains(keyword,PageRequest.of(page, 5));
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "home-page-questions";
    }*/
    @PostMapping("/search")
    public String searchQuestion(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                 @RequestParam(name = "loggedUser") String loggedUser) {
        List<User> users = this.userService.getAllUsers();
        if (!users.isEmpty()) {
            model.addAttribute("allUsers", users);
        }
        User user = this.userService.findUserByUsername(loggedUser);
        model.addAttribute("loggedUser", user);

        List<Question> sortedQuestions = questionService.getSortedQuestionsByFollowing(user, keyword);
        List<Question> otherQuestions = (List<Question>) questionService.findByTopicContains(keyword, PageRequest.of(0, 5));

        // Supprimez les questions déjà triées de la liste des autres questions
        otherQuestions.removeAll(sortedQuestions);

        // Ajoutez les autres questions après les questions triées
        sortedQuestions.addAll(otherQuestions);

        Page<Question> questions = new PageImpl<>(sortedQuestions, PageRequest.of(page, 5), sortedQuestions.size());
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "home-page-questions";
    }

    /*@GetMapping("/tag")
    public String processTags(Model model,
                              @RequestParam(name = "selectedTags") String selectedTags,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "loggedUser") String loggedUser) {
        List<User> users = this.userService.getAllUsers();
        if(!users.isEmpty()){
            model.addAttribute("allUsers",users);
        }
        User user = this.userService.findUserByUsername(loggedUser);
        model.addAttribute("loggedUser", user);

        String[] tagsArray = selectedTags.split(",");
        List<String> tagsList = Arrays.asList(tagsArray);
        List<Question> allQuestions = new ArrayList<>();
        for (String tag : tagsList) {
            Page<Question> questions = questionService.findByTag(tag, PageRequest.of(page, 5));
            allQuestions.addAll(questions.getContent());
        }
        Page<Question> questions = new PageImpl<>(allQuestions,PageRequest.of(page, 5), allQuestions.size());
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "home-page-questions";
    }*/
    @GetMapping("/tag")
    public String processTags(Model model,
                              @RequestParam(name = "selectedTags") String selectedTags,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "loggedUser") String loggedUser) {
        List<User> users = this.userService.getAllUsers();
        if (!users.isEmpty()) {
            model.addAttribute("allUsers", users);
        }
        User user = this.userService.findUserByUsername(loggedUser);
        model.addAttribute("loggedUser", user);

        String[] tagsArray = selectedTags.split(",");
        List<String> tagsList = Arrays.asList(tagsArray);
        List<Question> allQuestions = new ArrayList<>();
        for (String tag : tagsList) {
            List<Question> sortedQuestions = questionService.getSortedQuestionsByFollowing(user, tag);
            List<Question> otherQuestions = (List<Question>) questionService.findByTag(tag, PageRequest.of(0, 5));

            // Supprimez les questions déjà triées de la liste des autres questions
            otherQuestions.removeAll(sortedQuestions);

            // Ajoutez les autres questions après les questions triées
            sortedQuestions.addAll(otherQuestions);
            allQuestions.addAll(sortedQuestions);
        }
        Page<Question> questions = new PageImpl<>(allQuestions, PageRequest.of(page, 5), allQuestions.size());
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "home-page-questions";
    }

}
