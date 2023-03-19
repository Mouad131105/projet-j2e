package fr.uge.jee.ugeoverflow.vote;


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
}
