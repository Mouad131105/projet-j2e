package fr.uge.jee.ugeoverflow.publishing.answer;


import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.publishing.comment.CommentQuestionService;
import fr.uge.jee.ugeoverflow.publishing.question.QuestionService;
import fr.uge.jee.ugeoverflow.publishing.answer.AnswerService;
import fr.uge.jee.ugeoverflow.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

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