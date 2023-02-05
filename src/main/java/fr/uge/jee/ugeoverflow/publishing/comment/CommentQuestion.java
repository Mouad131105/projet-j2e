package fr.uge.jee.ugeoverflow.publishing.comment;

import fr.uge.jee.ugeoverflow.publishing.question.Question;
import fr.uge.jee.ugeoverflow.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CommentQuestion {
    private final long id;
    private final User author;
    private final Question parentQuestion;
    private String content;
    private LocalDateTime date;

}
