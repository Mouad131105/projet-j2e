package fr.uge.jee.ugeoverflow.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CommentQuestion")
public class CommentQuestion {
    @GeneratedValue
    @Id
    private long id;

    @NotNull(message = "Author cannot be null.")
    @ManyToOne
    private User author;

    @NotNull(message = "Parent question cannot be null.")
    @ManyToOne
    private Question parentQuestion;

    @NotBlank(message = "Content cannot be empty.")
    @Size(max = 255)
    private String content;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date = LocalDateTime.now();
}