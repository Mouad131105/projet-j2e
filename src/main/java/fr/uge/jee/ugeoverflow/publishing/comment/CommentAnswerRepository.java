package fr.uge.jee.ugeoverflow.publishing.comment;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentAnswerRepository extends CrudRepository<CommentAnswer, Long> {
    List<CommentAnswer> findAllByParentAnswerId(Long parentAnswerId);
    List<CommentAnswer> findAllByParentAnswerParentQuestion_Id(long id);
}
