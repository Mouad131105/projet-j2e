package fr.uge.jee.ugeoverflow.controller;

import fr.uge.jee.ugeoverflow.entities.Question;
import fr.uge.jee.ugeoverflow.entities.Tag;
import fr.uge.jee.ugeoverflow.service.QuestionService;
import fr.uge.jee.ugeoverflow.entities.User;
import fr.uge.jee.ugeoverflow.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;

    public QuestionController(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String questionForm(@RequestParam String username, Question question, Model model) {
        model.addAttribute("allTags", Arrays.asList(Tag.values()));

        User loggedUser = this.userService.findUserByUsername(username);
        if (loggedUser != null ) {
            model.addAttribute("loggedUser", loggedUser); //ajouté pour la deuxième facon de faire
            question.setAuthor(loggedUser);
            return "question-form";
        }
        model.addAttribute("selectedUserError", "User " + username + " cannot be found");
        return "question-form"; //normalement reviens sur la homepage car erreur sur l'utilisateur actuel
    }

    @PostMapping("/create")
    public String processForm(@RequestParam String username,@RequestParam String topic,
                              @RequestParam String content,@RequestParam Set<Tag> tags,
                              Model model) {

        Question question = new Question();
        question.setAuthor(this.userService.findUserByUsername(username));
        question.setTopic(topic); question.setContent(content);
        question.setTags(tags);

        Question createdQuestion = this.questionService.save(question);
        model.addAttribute("createdQuestion", createdQuestion);
        return "question-profile";
    }

    /*@PostMapping("/create")
    public String processForm(@ModelAttribute(name="question") @Valid Question question,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("allTags", Arrays.asList(Tag.values()));
            return "question-form";
        }

        Question createdQuestion = this.questionService.save(question);
        model.addAttribute("createdQuestion", createdQuestion);
        return "question-profile";
    }*/


    @GetMapping("/questions")
    public String questions(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Question> questions = questionService.findAll(page, 10);
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "questions";
    }

    @PostMapping("/search")
    public String searchQuestion(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword){
        Page<Question> questions = questionService.findByTopicContains(keyword,PageRequest.of(page, 10));
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "questions";
    }
}
