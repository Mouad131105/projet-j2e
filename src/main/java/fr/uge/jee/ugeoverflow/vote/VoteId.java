package fr.uge.jee.ugeoverflow.vote;


import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.user.User;


import java.io.Serializable;

public class VoteId implements Serializable {
    private User user;
    private Answer answer;
}
