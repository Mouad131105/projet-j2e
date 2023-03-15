package fr.uge.jee.ugeoverflow.publishing.comment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentQuestionRepository extends CrudRepository<CommentQuestion, Long> {
    List<CommentQuestion> findAllByParentQuestionId(Long parentQuestionId);
}
