package fr.uge.jee.ugeoverflow.publishing.question;

import fr.uge.jee.ugeoverflow.publishing.Tag;
import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.publishing.comment.CommentQuestion;
import fr.uge.jee.ugeoverflow.publishing.answer.AnswerService;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;
    private final CommentQuestionService commentQuestionService;
    private final AnswerService answerService;
    private final int pageSize = 5;

    public QuestionController(QuestionService questionService, UserService userService,
                              CommentQuestionService commentQuestionService, AnswerService answerService) {
        this.questionService = questionService;
        this.userService = userService;
        this.commentQuestionService = commentQuestionService;
        this.answerService = answerService;
    }
    private Page<Question> paginate(List<Question> allQuestions, int page, int pageSize) {
        List<List<Question>> pages = IntStream.range(0, (allQuestions.size() + pageSize - 1) / pageSize)
                .mapToObj(i -> allQuestions.subList(i * pageSize, Math.min((i + 1) * pageSize, allQuestions.size())))
                .collect(Collectors.toList());

        List<Question> requestedPage = pages.get(page);
        return new PageImpl<>(requestedPage, PageRequest.of(page, pageSize), allQuestions.size());
    }

    private void addUsersToModel(Model model) {
        List<User> users = userService.getAllUsers();
        if (!users.isEmpty()) {
            model.addAttribute("allUsers", users);
        }
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

                             Answer answer,
                             Model model) {

        Question question = this.questionService.findQuestionById(Long.valueOf(id));
        List<CommentQuestion> commentQuestions = this.commentQuestionService.findAllByParentQuestionId(question.getId());
        List<Answer> answers = this.answerService.findAllByParentQuestionId(question.getId());
        answers = answers.stream().sorted((a1, a2) -> Long.compare(a2.getScore(), a1.getScore())).collect(Collectors.toList());

        model.addAttribute("question", question);
        model.addAttribute("commentsQuestion", commentQuestions);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("answers", answers);

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

        question.setAnswers(Collections.emptyList());
        question.setComments(Collections.emptyList());
        Question createdQuestion = this.questionService.save(question);

        return "redirect:/question/profile/" + createdQuestion.getId() + "?loggedUser=" + loggedUser;
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

        addUsersToModel(model);

        User user = this.userService.findUserByUsername(loggedUser);
        model.addAttribute("loggedUser", user);


        List<Question> sortedQuestions = questionService.getSortedQuestionsByFollowing(user);
        List<Question> otherQuestions = new ArrayList<>(questionService.findAll());

        otherQuestions.removeAll(sortedQuestions);
        sortedQuestions.addAll(otherQuestions);

        Page<Question> questions = paginate(sortedQuestions, page, pageSize);
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("isTag", false);
        model.addAttribute("isSearch", false);
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
        model.addAttribute("isTag", false);
        model.addAttribute("isSearch", false);
        return "home-page-questions";
    }*/
    @RequestMapping(value = "/search", method = {RequestMethod.GET, RequestMethod.POST})
    public String searchQuestion(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                 @RequestParam(name = "loggedUser") String loggedUser) {
        addUsersToModel(model);
        User user = this.userService.findUserByUsername(loggedUser);
        model.addAttribute("loggedUser", user);

        List<Question> allSortedQuestions = questionService.getSortedQuestionsByFollowing(user);
        List<Question> otherQuestions = new ArrayList<>(questionService.findAll());
        otherQuestions.removeAll(allSortedQuestions);
        allSortedQuestions.addAll(otherQuestions);

        List<Question> questionsContainsKeyword = questionService.findByTopicContains(keyword);

        List<Question> commonQuestions = new ArrayList<>(allSortedQuestions);
        commonQuestions.retainAll(questionsContainsKeyword);

        Page<Question> questions = paginate(commonQuestions, page, pageSize);

        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("isTag", false);
        model.addAttribute("isSearch", true);
        model.addAttribute("keyword", keyword);
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

        addUsersToModel(model);
        User user = this.userService.findUserByUsername(loggedUser);
        model.addAttribute("loggedUser", user);

        String[] tagsArray = selectedTags.split(",");
        List<String> tagsList = Arrays.asList(tagsArray);
        List<Question> allQuestions = new ArrayList<>();
        for (String tag : tagsList) {
            List<Question> questions = new ArrayList<>(questionService.findByTag(tag));
            allQuestions.addAll(questions);
        }
        List<Question> allSortedQuestions = questionService.getSortedQuestionsByFollowing(user);
        List<Question> otherQuestions = new ArrayList<>(questionService.findAll());
        otherQuestions.removeAll(allSortedQuestions);
        allSortedQuestions.addAll(otherQuestions);

        List<Question> commonQuestions = new ArrayList<>(allSortedQuestions);
        commonQuestions.retainAll(allQuestions);
        Page<Question> questions = paginate(commonQuestions, page, pageSize);

        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("selectedTags",selectedTags);
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("isTag", true);
        model.addAttribute("isSearch", false);
        return "home-page-questions";
    }

}
