package fr.uge.jee.ugeoverflow.publishing.comment;

import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CommentAnswer {
    private final long id;
    private final User author;
    private final Answer parentAnswer;
    private String content;
    private LocalDateTime date;
}
