package fr.uge.jee.ugeoverflow.publishing.answer;

import fr.uge.jee.ugeoverflow.publishing.question.Question;
import fr.uge.jee.ugeoverflow.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Answers")
public class Answer {
    @GeneratedValue
    @Id
    private long id;

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "Answer_Id")
    private User author;

    @ManyToOne
    //@JoinColumn(name = "Question_Id")
    private Question parentQuestion;

    @NotNull
    @Positive
    private long score = 0;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date = LocalDateTime.now();

    private int upVotes;
    private int downVotes;
    private int comments;
}
