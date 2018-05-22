package library.services.SpringDataImpl;

import library.model.UncheckedBook;
import library.model.User;
import library.repository.NotificationRepository;
import library.repository.UncheckedBookRepository;
import library.repository.UserRepository;
import library.services.interfaces.BookService;
import library.services.interfaces.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class SpringDataUserServiceImpl implements UserService {
    private UserRepository userRepository;
    private NotificationRepository notificationRepository;
    private UncheckedBookRepository uncheckedBookRepository;

    private BookService bookService;

    public SpringDataUserServiceImpl(UserRepository userRepository,
                                     NotificationRepository notificationRepository,
                                     UncheckedBookRepository uncheckedBookRepository,
                                     BookService bookService) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.uncheckedBookRepository = uncheckedBookRepository;
        this.bookService = bookService;
    }

    @Override
    public Boolean isLoginExist(String login) {
        return userRepository.existsByLogin(login);
    }

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
        notificationRepository.deleteByUser(u);
        List<UncheckedBook> uncheckedBooks =
                uncheckedBookRepository.getUncheckedBooksByUser(u);
        uncheckedBooks.forEach(b -> bookService.refuseUncheckedBook(b));
        userRepository.deleteById(u.getId());
    }

    @Override
    public void deleteByLogin(String login) {
        userRepository.deleteUserByLogin(login);
    }


    @Override
    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public void updateFirstName(int id, String newFirstName) {
        User user = userRepository.getUserById(id);
        user.setFirstName(newFirstName);
        userRepository.save(user);
    }

    @Override
    public void updateLastName(int id, String newLastName) {
        User user = userRepository.getUserById(id);
        user.setLastName(newLastName);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(int id, String newPassword) {
        User user = userRepository.getUserById(id);
        String newPasswordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPasswordHash(newPasswordHash);
        userRepository.save(user);
    }
}
