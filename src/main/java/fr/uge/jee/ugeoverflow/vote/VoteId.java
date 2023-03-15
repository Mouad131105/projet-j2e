package fr.uge.jee.ugeoverflow.vote;

import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.user.User;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
public class VoteId implements Serializable {
    private final User user;
    private final Answer answer;
}
