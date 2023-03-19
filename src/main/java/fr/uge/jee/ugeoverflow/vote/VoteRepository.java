package fr.uge.jee.ugeoverflow.vote;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {
    void deleteAllByAnswerId(Long answerId);
    List<Vote> findAllByAnswer_ParentQuestion_Id(Long questionId);

}
