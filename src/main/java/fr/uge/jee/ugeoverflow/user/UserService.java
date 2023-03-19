package fr.uge.jee.ugeoverflow.user;

import fr.uge.jee.ugeoverflow.publishing.question.Question;
import fr.uge.jee.ugeoverflow.note.NoteRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


@Service
public class UserService {
    UserRepository userRepository;
    NoteRepository noteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public UserService(UserRepository userRepository, NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }
    public Iterable<User> saveAll(List<User> users){return this.userRepository.saveAll(users);}

    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public boolean existsUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    public String getPassword(String username) {
        return this.userRepository.getUserByPassword(username);
    }

    public List<Question> getAllQuestionFromUser(String username){
        return this.userRepository.getAllQuestionFromUser(username);
    }

    @Transactional
    public Set<User> findAllFollowedUsersFromUser(String username) {
        return this.userRepository.findAllFollowedUsersFromUser(username);
    }

    public boolean isEmailAlreadyUse(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public boolean isUsernameAlreadyUse(String username) {
        return this.userRepository.existsByUsername(username);
    }

    public User findUserWithEmail(String email) {
        return this.userRepository.findByEmail(email);
    }


    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }
}
