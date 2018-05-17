package library.services.interfaces;

import library.model.*;

import java.util.List;
import java.util.Set;

public interface DBService {
    /**
     * Get user by his login if it is exist
     *
     * @param login is the login of user
     * @return user with this login
     */
    User getUserByLogin(String login);

    /**
     * Get user by his id if it is exist
     *
     * @param id is the id of user
     * @return user with this id
     */
    User getUserByID(int id);

    /**
     * Create the user with these parameters in database.
     *
     * @param login        is the login og user
     * @param firstName    is the name of user
     * @param lastName     is the surname of user
     * @param passwordHash is the hash of password of user
     * @param role         is the user role (user or admin)
     * @return the user with these parameters and id
     */
    User createUser(String login, String firstName, String lastName,
                    String passwordHash, String role);

    /**
     * Delete the user with login {@code login} from database.
     *
     * @param id is the id of deleting user
     */
    void deleteUser(int id);

    /**
     * Replace old first name for user with login {@code userLogin} by {@code newFirstName}
     *
     * @param userID       is the id of user for which should be changed first name
     * @param newFirstName is the new first name
     */
    void updateUserFirstName(int userID, String newFirstName);

    /**
     * Replace old last name for user with login {@code userLogin} by {@code newLastName}
     *
     * @param userID      is the id of user for which should be changed last name
     * @param newLastName is the new last name
     */
    void updateUserLastName(int userID, String newLastName);

    /**
     * Replace old password for user with login {@code userLogin} by {@code newPasswordHash}
     *
     * @param userID          is the id of user for which should be changed password
     * @param newPasswordHash is the new password hash
     */
    void updateUserPassword(int userID, String newPasswordHash);

    /**
     * Get all users from database
     *
     * @return list of all users
     */
    //List<User> getAllUsers();

    /**
     * Create new book with following parameters in database
     *
     * @param bookName    is the name of new book
     * @param authorID    is the id of the author of this book
     * @param genre       is the genre of this book
     * @param category    is the category of this book
     * @param popularity  is the popularity level of this book
     * @param description is the description for this book
     */
    void createBook(String bookName, int authorID, Genre genre, Category category,
                    int popularity, String description);

    /**
     * Delete the book with id {@code id} from database
     *
     * @param id is the id of deleting book
     */
    void deleteBook(int id);

    /**
     * Create author with following parameters in database
     *
     * @param firstName  is the name of author
     * @param secondName is the patronymic of author
     * @param lastName   is the surname of author
     * @param century    is the century of his life
     */
    void createAuthor(String firstName, String secondName, String lastName, int century);

    /**
     * Create new genre in database
     *
     * @param genre is the name of new genre
     */
    void createGenre(Genre genre);

    /**
     * Get all books from database
     *
     * @return list of all books in database
     */
    List<Book> getAllBooks();

    /**
     * Get book with id {@code id}
     *
     * @param id is the id of book
     * @return book with this id
     */
    Book getBookByID(int id);

    /**
     * Get book id by its name and author id from database
     *
     * @param bookName is the name of book
     * @param authorID is the id of the author of this book
     * @return id of the searching book
     */
    int getBookIDByNameAndAuthor(String bookName, int authorID);

    /**
     * Get all genres from database
     *
     * @return list of all genres
     */
    List<Genre> getAllGenres();

    /**
     * Get all authors from database
     *
     * @return list of all authors
     */
    List<Author> getAllAuthors();

    /**
     * Get the author by his full name from database
     *
     * @param firstName  is the name of author
     * @param secondName is the patronymic of author
     * @param lastName   is the surname of author
     * @return author with these parameters
     */
    Author getAuthorByFullName(String firstName, String secondName, String lastName);

    /**
     * Get the author by his id from database
     *
     * @param id is the id of the author
     * @return searching author with this id
     */
    Author getAuthorByID(int id);

    /**
     * Get all categories of books from database
     *
     * @return list of all categories
     */
    List<Category> getAllCategories();

    /**
     * Get all notifications for user {@code userLogin} from database
     *
     * @param userID is the id of this user
     * @return list of all notifications for this user
     */
    List<Notification> getNotificationsForUser(int userID);

    /**
     * Create notification for user {@code userLogin} with text {@code text}
     * in database with current time
     *
     * @param userID is the id of user
     * @param text   is the text of notice
     * @param date   is the current date
     */
    Notification createNotificationForUser(int userID, String text, java.sql.Date date);

    /**
     * Get id of notification with following parameters
     *
     * @param userID is the id of user
     * @param text   is the notice text
     * @param date   is the date of notification
     * @return id of notification with these parameters
     */
    int getNotificationID(int userID, String text, java.sql.Date date);

    /**
     * Get notification with this id from database
     *
     * @param id is the id of notification
     * @return notification with this id
     */
    Notification getNotificationByID(int id);

    /**
     * Delete notification with id {@code id} from database
     *
     * @param id is the id of deleting notification
     */
    void deleteNotification(int id);

    /**
     * Get all unchecked books from database
     *
     * @return queue of unchecked books
     */
    Set<UncheckedBook> getAllUncheckedBooks();

    /**
     * Delete unchecked book with id {@code id} from database
     *
     * @param id is the id of deleting book
     */
    void deleteUncheckedBook(int id);

    /**
     * Create new unchecked book with following parameters in database
     *
     * @param userID      is a id of user who offers this book to the library
     * @param bookName    is a book name given it by the user
     * @param author      is the author surname or full name written by user
     * @param description is the description for book given by user
     * @return unchecked book with this parameters and id
     */
    UncheckedBook createUncheckedBook(int userID, String bookName, String author, String description);

    /**
     * Get unchecked book with id from database
     *
     * @param userID      is a id of user who offers this book to the library
     * @param bookName    is a book name given it by the user
     * @param author      is the author surname or full name written by user
     * @param description is the description for book given by user
     * @return id of the unchecked book with this parameters
     */
    int getIDOfUncheckedBook(int userID, String bookName, String author, String description);

    /**
     * Search books by sql string matching the parameters in it.
     *
     * @param sqlString  is the sql string
     * @param parameters is the parameters for this string in the required order
     * @return list of books searching by this request
     */
    List<Book> searchBooksByRequest(String sqlString, String... parameters);
}
