package library.services.interfaces;

import library.model.User;

public interface UserService {
    String ADMIN_ROLE = "admin";
    String USER_ROLE = "user";

    /**
     * Get all users of the library
     *
     * @return list of all library users
     */
    // List<User> getAllUsers();

    /**
     * Check is the user login {@code login} register in library
     *
     * @param login is the searching login
     * @return
     */
    Boolean isLoginExist(String login);

    /**
     * Create new user with following parameters in library
     *
     * @param login        is the login of new user, it should be unique
     * @param firstName    is the name of new user
     * @param lastName     is the surname of new user
     * @param passwordHash is the hash of his password
     * @param role         is the role of new user (administrator or not)
     * @return {@code true} if the login unique and the user was successfully register
     */
    Boolean register(String login, String firstName, String lastName,
                     String passwordHash, String role);

    /**
     * Delete the user {@code u}
     *
     * @param u is the deleting user
     */
    void delete(User u);

    void deleteByLogin(String login);

    /**
     * Get user by his unique login {@code login}
     *
     * @param login is the login of user
     * @return user with this login
     */
    User getUserByLogin(String login);

    /**
     * Replace old first name for user with login {@code userLogin} by {@code newFirstName}
     *
     * @param id           is the id of user for which should be changed first name
     * @param newFirstName is the new first name
     */
    void updateFirstName(int id, String newFirstName);

    /**
     * Replace old last name for user with login {@code userLogin} by {@code newLastName}
     *
     * @param id          is the id of user for which should be changed last name
     * @param newLastName is the new last name
     */
    void updateLastName(int id, String newLastName);

    /**
     * Replace old password for user with login {@code userLogin} by {@code newPassword}
     *
     * @param id              is the id of user for which should be changed password
     * @param newPassword is the new password hash
     */
    void updatePassword(int id, String newPassword);
}
