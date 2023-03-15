package fr.uge.jee.ugeoverflow.repository;

import fr.uge.jee.ugeoverflow.entities.Answer;
import fr.uge.jee.ugeoverflow.entities.Note;
import fr.uge.jee.ugeoverflow.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    @Query(value = "SELECT n.score FROM Note n WHERE n.author.username = :author AND n.receiverUsername = :receiver")
    Note findNoteFromReceiverAndAuthor(@Param("receiver") String receiver, @Param("author") String author);
}
