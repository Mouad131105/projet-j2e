package fr.uge.jee.ugeoverflow.vote;


import org.springframework.stereotype.Service;


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
