package fr.uge.jee.ugeoverflow.user;

import fr.uge.jee.ugeoverflow.publishing.question.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);
    User findUserByUsername(String username);
    String getUserByPassword(String username);
    List<User> findAll();

    @Query(value = "SELECT u.followedUsers FROM User u WHERE u.username = :username")
    Set<User> findAllFollowedUsersFromUser(@Param("username") String username);

    @Query(value = "SELECT q FROM Question q WHERE q.author.username = :username")
    List<Question> getAllQuestionFromUser(@Param("username") String username);
}
