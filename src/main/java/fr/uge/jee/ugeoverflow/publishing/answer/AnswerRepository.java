package fr.uge.jee.ugeoverflow.publishing.answer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findAllByParentQuestionId(Long parentQuestionId);
    Answer findAnswerById(Long id);
    void deleteAnswerById(Long id);
}
