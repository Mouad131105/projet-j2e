package fr.uge.jee.ugeoverflow.publishing.question;

import fr.uge.jee.ugeoverflow.publishing.Tag;
import fr.uge.jee.ugeoverflow.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
@Table(name = "Questions")
public class Question {
    @GeneratedValue
    @Id
    private long id;

    @NotBlank
    @ManyToOne
    private User author;

    @NotBlank
    @Size(max = 255)
    private String topic;

    @Size(max = 255)
    private String content;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Tag.class)
    @NotNull
    @NotEmpty(message = "The question must contain at least one tag")
    private Set<Tag> tags;
    private int comments;
    private int answers;

}
