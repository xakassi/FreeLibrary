package library.services.JDBCImpl;

import library.model.User;
import library.services.interfaces.DBService;
import library.services.interfaces.UserService;
import org.mindrot.jbcrypt.BCrypt;

public class JDBCUserServiceImpl implements UserService {
    private DBService dbService;

    public JDBCUserServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public Boolean isLoginExist(String login) {
        User user = dbService.getUserByLogin(login);
        return user != null;
    }

    @Override
    public Boolean register(String login, String firstName, String lastName,
                            String password, String role) {
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        if (isLoginExist(login)) {
            return false;
        }
        dbService.createUser(login, firstName, lastName, passwordHash, role);
        return true;
    }

    @Override
    public void delete(User u) {
        dbService.deleteUser(u.getId());
    }

    @Override
    public void deleteByLogin(String login) {

    }

    @Override
    public User getUserByLogin(String login) {
        return dbService.getUserByLogin(login);
    }

    @Override
    public User getUserById(int id) {
        return dbService.getUserByID(id);
    }

    @Override
    public void updateFirstName(int id, String newFirstName) {
        dbService.updateUserFirstName(id, newFirstName);
    }

    @Override
    public void updateLastName(int id, String newLastName) {
        dbService.updateUserLastName(id, newLastName);
    }

    @Override
    public void updatePassword(int id, String newPassword) {
        String newPasswordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        dbService.updateUserPassword(id, newPasswordHash);
    }
}
