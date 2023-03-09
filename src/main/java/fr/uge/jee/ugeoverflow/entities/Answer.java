package fr.uge.jee.ugeoverflow.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @NotNull
    @ManyToOne
    //@JoinColumn(name = "Question_Id")
    private Question parentQuestion;

    @NotBlank(message = "Content cannot be empty.")
    @Size(max = 255)
    private String content;

    @NotNull
    @Min(value = 0)
    private long score = 0;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date = LocalDateTime.now();

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Comment_Answers",
            joinColumns = @JoinColumn(name = "Answer_ID"),
            inverseJoinColumns = @JoinColumn(name = "Comment_Answer_ID")
    )
    private List<CommentAnswer> commentAnswers = new ArrayList<>();

    private int upVotes;
    private int downVotes;
    private int comments;
}
