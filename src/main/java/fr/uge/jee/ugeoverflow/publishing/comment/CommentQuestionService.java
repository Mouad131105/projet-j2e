package fr.uge.jee.ugeoverflow.publishing.comment;

import fr.uge.jee.ugeoverflow.publishing.comment.CommentQuestion;
import fr.uge.jee.ugeoverflow.publishing.comment.CommentQuestionRepository;
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
