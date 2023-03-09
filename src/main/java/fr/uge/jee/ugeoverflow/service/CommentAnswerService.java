package fr.uge.jee.ugeoverflow.service;

import fr.uge.jee.ugeoverflow.entities.CommentAnswer;
import fr.uge.jee.ugeoverflow.repository.CommentAnswerRepository;
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

    public List<CommentAnswer> findAllByParentAnswerId(Long parentAnswerId){
        return this.commentAnswerRepository.findAllByParentAnswerId(parentAnswerId);
    }
}
