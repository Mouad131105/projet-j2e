package fr.uge.jee.ugeoverflow.entities;

import fr.uge.jee.ugeoverflow.entities.Answer;
import fr.uge.jee.ugeoverflow.entities.User;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
public class VoteId implements Serializable {
    private final User user;
    private final Answer answer;
}
