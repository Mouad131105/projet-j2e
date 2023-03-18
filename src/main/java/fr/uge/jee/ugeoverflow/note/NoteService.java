package fr.uge.jee.ugeoverflow.note;

import fr.uge.jee.ugeoverflow.publishing.Tag;
import fr.uge.jee.ugeoverflow.publishing.question.Question;
import fr.uge.jee.ugeoverflow.publishing.question.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class NoteService {
    NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note save(Note note) {
        return this.noteRepository.save(note);
    }
}
