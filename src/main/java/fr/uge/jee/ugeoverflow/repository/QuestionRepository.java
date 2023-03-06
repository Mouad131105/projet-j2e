package fr.uge.jee.ugeoverflow.repository;

import fr.uge.jee.ugeoverflow.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByTopicContains(String kw, Pageable pageable);
    Question findQuestionById(Long id);
}
