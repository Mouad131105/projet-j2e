package fr.uge.jee.ugeoverflow.controller;

import fr.uge.jee.ugeoverflow.entities.*;
import fr.uge.jee.ugeoverflow.service.AnswerService;
import fr.uge.jee.ugeoverflow.service.CommentAnswerService;
import fr.uge.jee.ugeoverflow.service.CommentQuestionService;
import fr.uge.jee.ugeoverflow.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentQuestionService commentQuestionService;
    private final CommentAnswerService commentAnswerService;
    private final AnswerService answerService;
    private final UserService userService;

    public CommentController(CommentQuestionService commentQuestionService, CommentAnswerService commentAnswerService,
                             AnswerService answerService, UserService userService) {
        this.commentQuestionService = commentQuestionService;
        this.commentAnswerService = commentAnswerService;
        this.answerService = answerService;
        this.userService = userService;
    }


    @PostMapping("/question")
    public String processForm(@ModelAttribute(name = "commentQuestion") @Valid CommentQuestion commentQuestion,
                              BindingResult bindingResult, Model model,
                              @RequestParam String loggedUser) {

        User user = this.userService.findUserByUsername(loggedUser);
        commentQuestion.setAuthor(user);
        CommentQuestion comment = this.commentQuestionService.save(commentQuestion);
        long parentQuestionId = comment.getParentQuestion().getId();

        return "redirect:/question/profile/" + parentQuestionId + "?loggedUser=" + loggedUser;
    }

    @PostMapping("/answer")
    public String processForm(@ModelAttribute(name = "commentAnswer") @Valid CommentAnswer commentAnswer,
                              BindingResult bindingResult, Model model) {

        User user = this.userService.findUserByUsername(commentAnswer.getAuthor().getUsername());
        Answer answer = this.answerService.findAnswerById(commentAnswer.getParentAnswer().getId());
        commentAnswer.setAuthor(user);
        commentAnswer.setParentAnswer(answer);
        CommentAnswer comAnswer = this.commentAnswerService.save(commentAnswer);
        long parentQuestionId = comAnswer.getParentAnswer().getParentQuestion().getId();
        String loggedUser = comAnswer.getAuthor().getUsername();

        return "redirect:/question/profile/" + parentQuestionId + "?loggedUser=" + loggedUser;
    }
}
