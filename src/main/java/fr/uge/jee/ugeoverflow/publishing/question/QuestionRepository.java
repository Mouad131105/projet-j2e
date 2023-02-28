package fr.uge.jee.ugeoverflow.publishing.question;

import fr.uge.jee.ugeoverflow.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAll();
}
