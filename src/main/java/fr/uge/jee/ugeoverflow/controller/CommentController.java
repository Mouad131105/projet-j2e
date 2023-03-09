package fr.uge.jee.ugeoverflow.controller;

import fr.uge.jee.ugeoverflow.entities.Answer;
import fr.uge.jee.ugeoverflow.entities.CommentAnswer;
import fr.uge.jee.ugeoverflow.entities.CommentQuestion;
import fr.uge.jee.ugeoverflow.entities.Question;
import fr.uge.jee.ugeoverflow.service.AnswerService;
import fr.uge.jee.ugeoverflow.service.CommentAnswerService;
import fr.uge.jee.ugeoverflow.service.CommentQuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentQuestionService commentQuestionService;
    private final CommentAnswerService commentAnswerService;
    private final AnswerService answerService;

    public CommentController(CommentQuestionService commentQuestionService, CommentAnswerService commentAnswerService,
                             AnswerService answerService) {
        this.commentQuestionService = commentQuestionService;
        this.commentAnswerService = commentAnswerService;
        this.answerService = answerService;
    }


    @PostMapping("/question")
    public String processForm(@ModelAttribute(name = "commentQuestion") @Valid CommentQuestion commentQuestion,
                              BindingResult bindingResult,
                              Model model) {

        this.commentQuestionService.save(commentQuestion);
        Long parentQuestionId = commentQuestion.getParentQuestion().getId();
        String loggedUser = commentQuestion.getAuthor().getUsername();

        return "redirect:/question/profile/" + parentQuestionId + "?loggedUser=" + loggedUser;
    }

    @PostMapping("/answer")
    public String processForm(@ModelAttribute(name = "commentAnswer") @Valid CommentAnswer commentAnswer,
                              BindingResult bindingResult,
                              Model model) {

        Answer answer = commentAnswer.getParentAnswer();
        List<CommentAnswer> commentAnswers = answer.getCommentAnswers();
        commentAnswers.add(commentAnswer);
        answer.setCommentAnswers(commentAnswers);
        this.answerService.save(answer);
        Long parentAnswerId = commentAnswer.getParentAnswer().getParentQuestion().getId();
        String loggedUser = commentAnswer.getAuthor().getUsername();

        return "redirect:/question/profile/" + parentAnswerId + "?loggedUser=" + loggedUser;
    }
}
