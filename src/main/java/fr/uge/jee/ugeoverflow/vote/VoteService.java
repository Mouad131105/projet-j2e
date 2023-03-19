package fr.uge.jee.ugeoverflow.vote;


import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class VoteService {
    VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote save(Vote vote) {
        return this.voteRepository.save(vote);
    }

    @Transactional
    public void deleteAllByAnswerId(Long answerId){
        this.voteRepository.deleteAllByAnswerId(answerId);
    }

    public List<Vote> findAllByAnswer_ParentQuestion_Id(Long questionId){
        return this.voteRepository.findAllByAnswer_ParentQuestion_Id(questionId);
    }

    public Vote findByAnswerAndAndUserAndVoteType(Answer answer, User user, VoteType voteType){
        return this.voteRepository.findByAnswerAndAndUserAndVoteType(answer, user, voteType);
    }
}
