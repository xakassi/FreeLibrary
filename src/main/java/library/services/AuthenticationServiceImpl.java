package library.services;

import library.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationServiceImpl implements AuthenticationService {
    private UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Boolean authenticate(String login, String password) {
        User user = userService.getUserByLogin(login);
        return user != null && user.getLogin().equals(login) &&
                BCrypt.checkpw(password, user.getPasswordHash());
    }
}
