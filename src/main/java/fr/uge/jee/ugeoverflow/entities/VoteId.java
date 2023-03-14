package fr.uge.jee.ugeoverflow.entities;


import lombok.NonNull;
import java.io.Serializable;

public class VoteId implements Serializable {
    private User user;
    private Answer answer;
}
