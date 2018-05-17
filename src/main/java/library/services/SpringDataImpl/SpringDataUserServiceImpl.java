package library.services.SpringDataImpl;

import library.model.User;
import library.repository.UserRepository;
import library.services.interfaces.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class SpringDataUserServiceImpl implements UserService {
    private UserRepository userRepository;

    public SpringDataUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean isLoginExist(String login) {
        return userRepository.existsByLogin(login);
    }

    @Transactional
    @Override
    public Boolean register(String login, String firstName, String lastName, String password, String role) {
        if (isLoginExist(login)) {
            return false;
        }
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        userRepository.save(new User(login, firstName, lastName, passwordHash, role));
        return true;
    }

    @Override
    public void delete(User u) {
        userRepository.deleteById(u.getId());
    }

    @Override
    public void deleteByLogin(String login) {
        userRepository.deleteUserByLogin(login);
    }


    @Override
    public User getUserByLogin(String login) {
        return userRepository.getUsersByLogin(login);
    }

    @Override
    public void updateFirstName(int id, String newFirstName) {
        User user = userRepository.getUsersById(id);
        user.setFirstName(newFirstName);
        userRepository.save(user);
    }

    @Override
    public void updateLastName(int id, String newLastName) {
        User user = userRepository.getUsersById(id);
        user.setLastName(newLastName);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(int id, String newPassword) {
        User user = userRepository.getUsersById(id);
        String newPasswordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPasswordHash(newPasswordHash);
        userRepository.save(user);
    }
}
