package fr.uge.jee.ugeoverflow.controller;

import fr.uge.jee.ugeoverflow.entities.Answer;
import fr.uge.jee.ugeoverflow.entities.CommentQuestion;
import fr.uge.jee.ugeoverflow.entities.Question;
import fr.uge.jee.ugeoverflow.entities.User;
import fr.uge.jee.ugeoverflow.service.AnswerService;
import fr.uge.jee.ugeoverflow.service.CommentQuestionService;
import fr.uge.jee.ugeoverflow.service.QuestionService;
import fr.uge.jee.ugeoverflow.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService questionService;
    private final UserService userService;
    private final CommentQuestionService commentQuestionService;
    private final AnswerService answerService;

    public AnswerController(QuestionService questionService, UserService userService,
                              CommentQuestionService commentQuestionService, AnswerService answerService) {
        this.questionService = questionService;
        this.userService = userService;
        this.commentQuestionService = commentQuestionService;
        this.answerService = answerService;
    }

    @PostMapping("/add")
    public String addAnswer(@ModelAttribute(name = "answer") @Valid Answer answer,
                            BindingResult bindingResult,
                            Model model) {
        answerService.save(answer);
        Long parentQuestionId = answer.getParentQuestion().getId();
        String loggedUser = answer.getAuthor().getUsername();

        return "redirect:/question/profile/" + parentQuestionId + "?loggedUser=" + loggedUser;
    }
}