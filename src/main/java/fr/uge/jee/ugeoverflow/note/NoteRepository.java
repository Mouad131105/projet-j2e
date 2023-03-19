package fr.uge.jee.ugeoverflow.note;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    @Query(value = "SELECT n.score FROM Note n WHERE n.author.username = :author AND n.receiverUsername = :receiver")
    Note findNoteFromReceiverAndAuthor(@Param("receiver") String receiver, @Param("author") String author);
}
