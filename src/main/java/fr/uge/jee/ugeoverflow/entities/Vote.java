package fr.uge.jee.ugeoverflow.entities;

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
