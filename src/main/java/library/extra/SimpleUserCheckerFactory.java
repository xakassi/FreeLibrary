package library.extra;

public class SimpleUserCheckerFactory extends UserCheckerFactory {

    @Override
    public UserChecker createUserChecker() {
        UserChecker passwordsMatchesChecker = new PasswordsMatchesChecker(null);
        UserChecker fillFieldsChecker = new FillFieldsChecker(passwordsMatchesChecker);
        return fillFieldsChecker;
    }
}
