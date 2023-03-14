package fr.uge.jee.ugeoverflow.repository;

import fr.uge.jee.ugeoverflow.entities.CommentAnswer;
import fr.uge.jee.ugeoverflow.entities.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentAnswerRepository extends CrudRepository<CommentAnswer, Long> {
    List<CommentAnswer> findAllByParentAnswerId(Long parentAnswerId);
    List<CommentAnswer> findAllByParentAnswerParentQuestion_Id(long id);
}
