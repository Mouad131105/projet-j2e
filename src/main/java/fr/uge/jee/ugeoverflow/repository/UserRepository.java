package fr.uge.jee.ugeoverflow.repository;

import fr.uge.jee.ugeoverflow.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);
    User findUserByUsername(String username);
    String getUserByPassword(String username);
}
