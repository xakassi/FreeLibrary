package library.extra;

import library.model.User;

public abstract class BaseUserChecker implements UserChecker {
    protected UserChecker nextUserChecker;

    protected BaseUserChecker(UserChecker nextUserChecker) {
        this.nextUserChecker = nextUserChecker;
    }

    @Override
    public abstract String check(User user, String newPassword);

    protected String nextCheck(User user, String newPassword) {
        if (nextUserChecker != null) {
            return nextUserChecker.check(user, newPassword);
        }
        return "Success!";
    }
}
