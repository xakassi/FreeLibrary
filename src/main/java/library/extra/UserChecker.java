package library.extra;

import library.model.User;

public interface UserChecker {
    String check(User user, String newPassword);
}
