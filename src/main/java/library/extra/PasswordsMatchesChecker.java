package library.extra;

import library.model.User;

public class PasswordsMatchesChecker extends BaseUserChecker {
    PasswordsMatchesChecker(UserChecker nextUserChecker) {
        super(nextUserChecker);
    }

    @Override
    public String check(User user, String passwordConfirm) {
        String password = user.getPasswordHash();
        if (!password.equals(passwordConfirm)) {
            return "Passwords do not match!";
        }
        return nextCheck(user, passwordConfirm);
    }
}
