package library.extra;

import library.model.User;

public class FillFieldsChecker extends BaseUserChecker {
    FillFieldsChecker(UserChecker nextUserChecker) {
        super(nextUserChecker);
    }

    @Override
    public String check(User user, String newPassword) {
        String fName = user.getFirstName().trim();
        String lName = user.getLastName().trim();
        String login = user.getLogin().trim();
        String password = user.getPasswordHash().trim();
        String password2 = newPassword.trim();
        if (fName.equals("") || lName.equals("")
                || login.equals("") || password.equals("")
                || password2.equals("")) {
            return "All fields should be filled!";
        }
        return nextCheck(user, newPassword);
    }
}
