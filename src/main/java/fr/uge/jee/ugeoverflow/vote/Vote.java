package fr.uge.jee.ugeoverflow.vote;

import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@RequiredArgsConstructor
@IdClass(VoteId.class)
public class Vote {
    @Id
    private final User user;
    @Id
    private final Answer answer;
    private final VoteType voteType;
}
