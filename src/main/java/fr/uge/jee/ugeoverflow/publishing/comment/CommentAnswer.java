package fr.uge.jee.ugeoverflow.publishing.comment;

import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class CommentAnswer {
    @GeneratedValue
    @Id
    private long id;

    @NotNull(message = "Author cannot be null.")
    @ManyToOne
    private User author;

    @NotNull(message = "Parent answer cannot be null.")
    @ManyToOne
    private Answer parentAnswer;

    @NotBlank(message = "Content cannot be empty.")
    @Size(max = 255)
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date = LocalDateTime.now();
}
