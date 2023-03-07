package fr.uge.jee.ugeoverflow.service;

import fr.uge.jee.ugeoverflow.entities.CommentQuestion;
import fr.uge.jee.ugeoverflow.repository.CommentQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentQuestionService {
    CommentQuestionRepository commentQuestionRepository;
    public CommentQuestionService(CommentQuestionRepository commentQuestionRepository) {
        this.commentQuestionRepository = commentQuestionRepository;
    }

    public CommentQuestion save(CommentQuestion commentQuestion) {
        return this.commentQuestionRepository.save(commentQuestion);
    }

    public List<CommentQuestion> findAllByParentQuestionId(Long parentQuestionId){
        return this.commentQuestionRepository.findAllByParentQuestionId(parentQuestionId);
    }
}
