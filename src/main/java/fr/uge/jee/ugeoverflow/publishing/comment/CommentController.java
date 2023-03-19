package fr.uge.jee.ugeoverflow.publishing.comment;

import fr.uge.jee.ugeoverflow.authentication.AuthenticationService;
import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.publishing.comment.CommentAnswer;
import fr.uge.jee.ugeoverflow.publishing.comment.CommentQuestion;
import fr.uge.jee.ugeoverflow.publishing.comment.CommentQuestionService;
import fr.uge.jee.ugeoverflow.publishing.answer.AnswerService;
import fr.uge.jee.ugeoverflow.publishing.comment.CommentAnswerService;
import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentQuestionService commentQuestionService;
    private final CommentAnswerService commentAnswerService;
    private final AnswerService answerService;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public CommentController(CommentQuestionService commentQuestionService, CommentAnswerService commentAnswerService,
                             AnswerService answerService, UserService userService, AuthenticationService authenticationService) {
        this.commentQuestionService = commentQuestionService;
        this.commentAnswerService = commentAnswerService;
        this.answerService = answerService;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/question")
    public String processForm(@ModelAttribute(name = "commentQuestion") @Valid CommentQuestion commentQuestion,
                              BindingResult bindingResult, Model model) {

        User user = this.authenticationService.getLoggedUser();
        commentQuestion.setAuthor(user);
        CommentQuestion comment = this.commentQuestionService.save(commentQuestion);
        long parentQuestionId = comment.getParentQuestion().getId();

        return "redirect:/question/profile/" + parentQuestionId;
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

        return "redirect:/question/profile/" + parentQuestionId;
    }
}