package fr.uge.jee.ugeoverflow.entities;

import fr.uge.jee.ugeoverflow.publishing.question.Question;
import fr.uge.jee.ugeoverflow.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

public enum CommentType {
    Answer,
    Question
}

