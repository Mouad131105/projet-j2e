package fr.uge.jee.ugeoverflow.vote;


import fr.uge.jee.ugeoverflow.vote.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

}
