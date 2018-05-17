package library.services.JDBCImpl;

import library.model.User;
import library.services.interfaces.AuthenticationService;
import library.services.interfaces.UserService;
import org.mindrot.jbcrypt.BCrypt;

public class JDBCAuthenticationServiceImpl implements AuthenticationService {
    private UserService userService;

    public JDBCAuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Boolean authenticate(String login, String password) {
        User user = userService.getUserByLogin(login);
        return user != null && user.getLogin().equals(login) &&
                BCrypt.checkpw(password, user.getPasswordHash());
    }
}
