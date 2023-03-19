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

    /*@Transactional
    public Set<User> findAllFollowedUsersFromUser(String username){
        Set<User> test = this.userRepository.findAllFollowedUsersFromUser(username);
        Hibernate.initialize(test);
        return test;
    }

    @Transactional
    public Set<User> findAllFollowedUsersFromUser(String username){
        User user = this.userRepository.findUserByUsername(username);
        Set<User> followeduser = user.getFollowedUsers();
        Hibernate.initialize(followeduser);
        return followeduser;
    }*/

    @PostConstruct
    private void postConstruct() {

        /*User user2 = new User(); user2.setUsername("Dorian"); user2.setEmail("dorian@gmail.com"); user2.setPassword("Dorian123!");
        User user3 = new User(); user3.setUsername("Robert"); user3.setEmail("robert@gmail.com"); user3.setPassword("Robert123!");
        User user4 = new User(); user4.setUsername("Louise"); user4.setEmail("louise@gmail.com"); user4.setPassword("Louise123!");
        User user5 = new User(); user5.setUsername("Sascha"); user5.setEmail("sascha@gmail.com"); user5.setPassword("Sascha123!");
        User user6 = new User(); user6.setUsername("Pauline"); user6.setEmail("pauline@gmail.com"); user6.setPassword("Pauline123!");
        User user7 = new User(); user7.setUsername("Laurine"); user7.setEmail("laurine@gmail.com"); user7.setPassword("Laurine123!");

        List<User> predefinedUsers = Arrays.asList(user2,user3,user4,user5,user6,user7);

        userRepository.saveAll(predefinedUsers);*/
    }
}
