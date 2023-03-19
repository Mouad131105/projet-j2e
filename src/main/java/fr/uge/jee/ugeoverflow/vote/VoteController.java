package fr.uge.jee.ugeoverflow.vote;
import fr.uge.jee.ugeoverflow.authentication.AuthenticationService;
import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.publishing.answer.AnswerService;
import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserService;
import fr.uge.jee.ugeoverflow.vote.Vote;
import fr.uge.jee.ugeoverflow.vote.VoteService;
import fr.uge.jee.ugeoverflow.vote.VoteType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/vote")
public class VoteController {
    private final VoteService voteService;
    private final UserService userService;
    private final AnswerService answerService;
    private final AuthenticationService authenticationService;

    public VoteController(VoteService voteService,UserService userService,
                          AnswerService answerService, AuthenticationService authenticationService) {
        this.voteService = voteService;
        this.answerService = answerService;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/add/up")
    public String voteUp(@RequestParam Long answerId) {

        User user = this.authenticationService.getLoggedUser();
        Answer answer = this.answerService.findAnswerById(answerId);
        answer.setScore(answer.getScore() + 1);
        answerService.save(answer);
        voteService.save(new Vote(user, answer, VoteType.UP_VOTE));
        long parentQuestionId = answer.getParentQuestion().getId();

        return "redirect:/question/profile/" + parentQuestionId + "?loggedUser=" + user.getUsername();
    }

    @PostMapping("/add/down")
    public String voteDown(@RequestParam Long answerId) {

        User user = this.authenticationService.getLoggedUser();
        Answer answer = this.answerService.findAnswerById(answerId);
        answer.setScore(answer.getScore() - 1);
        answerService.save(answer);
        voteService.save(new Vote(user, answer, VoteType.DOWN_VOTE));
        long parentQuestionId = answer.getParentQuestion().getId();

        return "redirect:/question/profile/" + parentQuestionId + "?loggedUser=" + user.getUsername();
    }
}
