package fr.uge.jee.ugeoverflow.publishing.comment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentAnswerRepository extends CrudRepository<CommentAnswer, Long> {

}
