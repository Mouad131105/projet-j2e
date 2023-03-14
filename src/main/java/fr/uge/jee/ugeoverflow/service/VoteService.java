package fr.uge.jee.ugeoverflow.service;

import fr.uge.jee.ugeoverflow.entities.CommentAnswer;
import fr.uge.jee.ugeoverflow.entities.Vote;
import fr.uge.jee.ugeoverflow.repository.CommentAnswerRepository;
import fr.uge.jee.ugeoverflow.repository.VoteRepository;
import org.springframework.stereotype.Service;

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

}
