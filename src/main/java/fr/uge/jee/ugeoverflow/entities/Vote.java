package fr.uge.jee.ugeoverflow.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@IdClass(VoteId.class)
@NoArgsConstructor
@Entity
@Table(name = "Votes")
public class Vote {

    @Id
    @NotNull
    @ManyToOne
    private User user;

    @Id
    @NotNull
    @ManyToOne
    private Answer answer;

    @Enumerated
    //@ElementCollection(targetClass = VoteType.class, fetch = FetchType.EAGER)
    @Column
    @NotNull
    private VoteType voteType;

    public Vote(User user, Answer answer, VoteType voteType) {
        this.user = user;
        this.answer = answer;
        this.voteType = voteType;
    }
}
