package library.repository;

import library.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User getUserByLogin(String login);
    User getUserById(int id);

    void deleteUserByLogin(String login);
    boolean existsByLogin(String login);

}
