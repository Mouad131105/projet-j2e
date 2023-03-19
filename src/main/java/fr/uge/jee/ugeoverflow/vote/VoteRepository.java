package fr.uge.jee.ugeoverflow.vote;


import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {
    void deleteAllByAnswerId(Long answerId);
    List<Vote> findAllByAnswer_ParentQuestion_Id(Long questionId);
    Vote findByAnswerAndAndUserAndVoteType(Answer answer, User user, VoteType voteType);
}
