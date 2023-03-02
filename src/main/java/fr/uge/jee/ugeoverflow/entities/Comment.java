package fr.uge.jee.ugeoverflow.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Comment {
    private final long id;
    private final User author;
    private final Answer parentAnswer;
    private String content;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private CommentType type;

}