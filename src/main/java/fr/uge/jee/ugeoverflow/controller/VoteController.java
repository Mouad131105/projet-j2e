package fr.uge.jee.ugeoverflow.controller;
import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.service.*;
import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserService;
import fr.uge.jee.ugeoverflow.vote.Vote;
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

    public VoteController(VoteService voteService,UserService userService,
                          AnswerService answerService) {
        this.voteService = voteService;
        this.answerService = answerService;
        this.userService = userService;
    }


    @PostMapping("/add/up")
    public String voteUp(@RequestParam Long answerId,
                              @RequestParam String loggedUser) {

        User user = this.userService.findUserByUsername(loggedUser);
        Answer answer = this.answerService.findAnswerById(answerId);
        answer.setScore(answer.getScore() + 1);
        answerService.save(answer);
        voteService.save(new Vote(user, answer, VoteType.UP_VOTE));
        long parentQuestionId = answer.getParentQuestion().getId();

        return "redirect:/question/profile/" + parentQuestionId + "?loggedUser=" + loggedUser;
    }

    @PostMapping("/add/down")
    public String voteDown(@RequestParam Long answerId,
                         @RequestParam String loggedUser) {

        User user = this.userService.findUserByUsername(loggedUser);
        Answer answer = this.answerService.findAnswerById(answerId);
        answer.setScore(answer.getScore() - 1);
        answerService.save(answer);
        voteService.save(new Vote(user, answer, VoteType.DOWN_VOTE));
        long parentQuestionId = answer.getParentQuestion().getId();

        return "redirect:/question/profile/" + parentQuestionId + "?loggedUser=" + loggedUser;
    }
}
