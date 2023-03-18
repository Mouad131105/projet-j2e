package fr.uge.jee.ugeoverflow.publishing.question;

import fr.uge.jee.ugeoverflow.publishing.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByTopicContains(String kw, Pageable pageable);
    Question findQuestionById(Long id);

    Page<Question> findByTagsContaining(@NotNull(message = "Tags cannot be null.") @NotEmpty(message = "The question must contain at least one tag.") Set<Tag> tags, Pageable pageable);

}