package library.services;

public interface AuthenticationService {
    /**
     * Identify user by login {@code login} and authenticate him by hashed password {@code passwordHash}
     *
     * @param login    is the login for identification
     * @param password is the hash of user password for authentication
     * @return {@code true} if the login and password hash are exist and correct
     */
    Boolean authenticate(String login, String password);
}
