package fr.uge.jee.ugeoverflow.service;


import fr.uge.jee.ugeoverflow.publishing.comment.CommentAnswer;
import fr.uge.jee.ugeoverflow.publishing.comment.CommentAnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentAnswerService {
    CommentAnswerRepository commentAnswerRepository;
    public CommentAnswerService(CommentAnswerRepository commentAnswerRepository) {
        this.commentAnswerRepository = commentAnswerRepository;
    }

    public CommentAnswer save(CommentAnswer commentAnswer) {
        return this.commentAnswerRepository.save(commentAnswer);
    }

    public List<CommentAnswer> findAllByParentAnswerParentQuestion_Id(Long parentAnswerId){
        return this.commentAnswerRepository.findAllByParentAnswerParentQuestion_Id(parentAnswerId);
    }
}
