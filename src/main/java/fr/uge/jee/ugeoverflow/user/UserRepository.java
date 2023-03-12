package fr.uge.jee.ugeoverflow.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);
    User findUserByUsername(String username);
    User findByEmail(String email);
    String getUserByPassword(String username);
}
