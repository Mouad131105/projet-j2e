package fr.uge.jee.ugeoverflow.repository;

import fr.uge.jee.ugeoverflow.entities.CommentAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentAnswerRepository extends CrudRepository<CommentAnswer, Long> {

}
