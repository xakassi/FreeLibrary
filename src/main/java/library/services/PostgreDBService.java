package library.services;

import library.config.ConnectionSettings;
import library.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PostgreDBService implements DBService {
    private ConnectionSettings connectionSettings;

    private static final Logger LOG = LoggerFactory.getLogger(PostgreDBService.class.getName());
    private static final Marker FATAL = MarkerFactory.getMarker("FATAL");

    public PostgreDBService(ConnectionSettings connectionSettings) {
        this.connectionSettings = connectionSettings;
    }

    @PostConstruct
    private void start() {
        connect();
    }

    private void connect() {
        try {
            Class.forName(connectionSettings.getDriverClassName());
        } catch (ClassNotFoundException e) {
            LOG.error(FATAL, "Can't connect to the database! Class PostgreDBService, method connect().");
        }
    }

    @Override
    public User createUser(String login, String firstName, String lastName,
                           String passwordHash, String role) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO libuser " +
                             "(login, firstName, lastName, passwordHash, urole) " +
                             "VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, login);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, passwordHash);
            stmt.setString(5, role);
            stmt.execute();
            return getUserByLogin(login);
        } catch (SQLException e) {
            LOG.error("Can't create the user '{}' in database! " +
                            "Class PostgreDBService, method createUser().",
                    login, e);
        }
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id, login, firstName, lastName, passwordHash, urole FROM libuser" +
                             " WHERE login=?")) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4),
                            rs.getString(5), rs.getString(6));
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't get user by login {} from database! " +
                    "Class PostgreDBService, method getUserByLogin().", login, e);
        }
        return null;
    }

    @Override
    public User getUserByID(int id) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id, login, firstName, lastName, passwordHash, urole FROM libuser" +
                             " WHERE id=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4),
                            rs.getString(5), rs.getString(6));
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't get user by id {} from database! " +
                    "Class PostgreDBService, method getUserByID().", id, e);
        }
        return null;
    }

    @Override
    public void deleteUser(int id) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "DELETE FROM libuser WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Can't delete the user '{}' from database! " +
                    "Class PostgreDBService, method deleteUser().", id, e);
        }
    }

    @Override
    public void updateUserFirstName(int userID, String newFirstName) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE libuser SET firstname=? WHERE id=?")) {
            stmt.setString(1, newFirstName);
            stmt.setInt(2, userID);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Can't update user first name '{}' for user login {} from database! " +
                            "Class PostgreDBService, method updateUserFirstName().",
                    newFirstName, userID, e);
        }
    }

    @Override
    public void updateUserLastName(int userID, String newLastName) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE libuser SET lastname=? WHERE id=?")) {
            stmt.setString(1, newLastName);
            stmt.setInt(2, userID);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Can't update user last name '{}' for user login {} from database! " +
                            "Class PostgreDBService, method updateUserLastName().",
                    newLastName, userID, e);
        }
    }

    @Override
    public void updateUserPassword(int userID, String newPasswordHash) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE libuser SET passwordhash=? WHERE id=?")) {
            stmt.setString(1, newPasswordHash);
            stmt.setInt(2, userID);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Can't update user password '{}' for user login {} from database! " +
                            "Class PostgreDBService, method updateUserPassword().",
                    newPasswordHash, userID, e);
        }
    }

    @Override
    public void createBook(String bookName, int authorID, Genre genre, Category category,
                           int popularity, String description) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO book " +
                             "(name, authorid, genreid, categoryid, popularity, description) " +
                             "VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, bookName);
            stmt.setInt(2, authorID);
            stmt.setString(3, genre.getName());
            stmt.setString(4, category.getName());
            stmt.setInt(5, popularity);
            stmt.setString(6, description);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Can't create the book '{}' in database! " +
                    "Class PostgreDBService, method createBook().", bookName, e);
        }
    }

    @Override
    public void deleteBook(int id) {
        synchronized (this) {
            try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                    connectionSettings.getUsername(), connectionSettings.getPassword());
                 PreparedStatement stmt = connection.prepareStatement(
                         "DELETE FROM book WHERE id=?")) {
                stmt.setInt(1, id);
                stmt.execute();
            } catch (SQLException e) {
                LOG.error("Can't delete the book with id {} from database! " +
                        "Class PostgreDBService, method deleteBook().", id, e);
            }
        }
    }

    @Override
    public void createAuthor(String firstName, String secondName, String lastName, int century) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO author " +
                             "(firstName, secondName, lastName, century) " +
                             "VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, firstName);
            stmt.setString(2, secondName);
            stmt.setString(3, lastName);
            stmt.setInt(4, century);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Can't create the author {} {} {} in database! " +
                            "Class PostgreDBService, method createAuthor().",
                    firstName, secondName, lastName, e);
        }
    }

    @Override
    public void createGenre(Genre genre) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO genre (name) VALUES (?)")) {
            stmt.setString(1, genre.getName());
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Can't create genre '{}' in database! " +
                    "Class PostgreDBService, method createGenre().", genre, e);
        }
    }


    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id, name, authorid, genreid, categoryid, " +
                             "popularity, description FROM book");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Author author = getAuthorByID(rs.getInt(3));
                Book book = new Book(rs.getInt(1), rs.getString(2), author,
                        new Genre(rs.getString(4)),
                        new Category(rs.getString(5)),
                        rs.getInt(6), rs.getString(7));
                books.add(book);
            }
        } catch (SQLException e) {
            LOG.error("Can't get all books from database! " +
                    "Class PostgreDBService, method getAllBooks().", e);
        }
        return books;
    }

    @Override
    public Book getBookByID(int id) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id, name, authorid, genreid, categoryid, " +
                             "popularity, description FROM book WHERE id=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Author author = getAuthorByID(rs.getInt(3));
                    return new Book(rs.getInt(1), rs.getString(2), author,
                            new Genre(rs.getString(4)),
                            new Category(rs.getString(5)),
                            rs.getInt(6), rs.getString(7));
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't get book by id {} from database! " +
                            "Class PostgreDBService, method getBookIDByNameAndAuthor().",
                    id, e);
        }
        return null;
    }

    @Override
    public int getBookIDByNameAndAuthor(String bookName, int authorID) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id FROM book WHERE name=? AND authorid=?")) {
            stmt.setString(1, bookName);
            stmt.setInt(2, authorID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't get book by name '{}' and author id = {} from database! " +
                            "Class PostgreDBService, method getBookIDByNameAndAuthor().",
                    bookName, authorID, e);
        }
        return 0;
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT name FROM genre");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                genres.add(new Genre(rs.getString(1)));
            }
        } catch (SQLException e) {
            LOG.error("Can't get all genres from database! " +
                    "Class PostgreDBService, method getAllGenres().", e);
        }
        return genres;
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id, firstname, secondname, lastname, century FROM author");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                authors.add(new Author(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5)));
            }
        } catch (SQLException e) {
            LOG.error("Can't get all authors from database! " +
                    "Class PostgreDBService, method getAllAuthors().", e);
        }
        return authors;
    }

    @Override
    public Author getAuthorByFullName(String firstName, String secondName, String lastName) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id, firstname, secondname, lastname, century FROM author" +
                             " WHERE firstname=? AND secondname=? AND lastname=?")) {
            stmt.setString(1, firstName);
            stmt.setString(2, secondName);
            stmt.setString(3, lastName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Author(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getInt(5));
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't get author by full name {} {} {} from database! " +
                            "Class PostgreDBService, method getAuthorByFullName().",
                    firstName, secondName, lastName, e);
        }
        return null;
    }

    @Override
    public Author getAuthorByID(int id) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id, firstname, secondname, lastname, century FROM author" +
                             " WHERE id=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Author(rs.getInt(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getInt(5));
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't get author by id = {} from database! " +
                            "Class PostgreDBService, method getAuthorByID().",
                    id, e);
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT name FROM category");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(new Category(rs.getString(1)));
            }
        } catch (SQLException e) {
            LOG.error("Can't get all categories from database! " +
                    "Class PostgreDBService, method getAllCategories().", e);
        }
        return categories;
    }

    @Override
    public List<Notification> getNotificationsForUser(int userID) {
        List<Notification> notifications = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id, userid, notice, notifdate FROM notification" +
                             " WHERE userid=?")) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notifications.add(new Notification(rs.getInt(1), userID,
                            rs.getString(3), rs.getDate(4)));
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't get notifications for user {} from database! " +
                    "Class PostgreDBService, method getNotificationsForUser().", userID, e);
        }
        return notifications;
    }

    @Override
    public Notification createNotificationForUser(int userID, String text, java.sql.Date date) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO notification (userid, notice, notifdate) VALUES (?,?,?)")) {
            stmt.setInt(1, userID);
            stmt.setString(2, text);
            stmt.setDate(3, date);
            stmt.execute();
            int id = getNotificationID(userID, text, date);
            return new Notification(id, userID, text, date);
        } catch (SQLException e) {
            LOG.error("Can't create notification '{}' for user {} in database! " +
                            "Class PostgreDBService, method createNotificationForUser().",
                    text, userID, e);
        }
        return null;
    }

    @Override
    public int getNotificationID(int userID, String text, Date date) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id FROM notification" +
                             " WHERE userid=? AND notice=? AND notifdate=?")) {
            stmt.setInt(1, userID);
            stmt.setString(2, text);
            stmt.setDate(3, date);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't get notification id for user {}" +
                            "with text '{}' and date {} from database! " +
                            "Class PostgreDBService, method getNotificationID().",
                    userID, text, date, e);
        }
        return 0;
    }

    @Override
    public Notification getNotificationByID(int id) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id, userid, notice, notifdate FROM notification" +
                             " WHERE id=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Notification(id, rs.getInt(2), rs.getString(3),
                            rs.getDate(4));
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't get notification by id {}!" +
                            "Class PostgreDBService, method getNotificationID().",
                    id, e);
        }
        return null;
    }

    @Override
    public void deleteNotification(int id) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "DELETE FROM notification WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Can't delete notification with id {} from database! " +
                    "Class PostgreDBService, method deleteNotification().", id, e);
        }
    }

    @Override
    public Set<UncheckedBook> getAllUncheckedBooks() {
        Set<UncheckedBook> uncheckedBookSet = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id, userid, bookname, author, description FROM uncheckedbook");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                uncheckedBookSet.add(
                        new UncheckedBook(rs.getInt(1), rs.getInt(2), rs.getString(3),
                                rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            LOG.error("Can't get all unchecked books from database! " +
                    "Class PostgreDBService, method getAllUncheckedBooks().", e);
        }
        return uncheckedBookSet;
    }

    @Override
    public void deleteUncheckedBook(int id) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "DELETE FROM uncheckedbook WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Can't delete theunchecked book with id {} from database! " +
                    "Class PostgreDBService, method deleteUncheckedBook().", id, e);
        }
    }

    @Override
    public UncheckedBook createUncheckedBook(int userID, String bookName,
                                             String author, String description) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO uncheckedbook " +
                             "(userid, bookname, author, description) " +
                             "VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, userID);
            stmt.setString(2, bookName);
            stmt.setString(3, author);
            stmt.setString(4, description);
            stmt.execute();
            int id = getIDOfUncheckedBook(userID, bookName, author, description);
            return new UncheckedBook(id, userID, bookName, author, description);
        } catch (SQLException e) {
            LOG.error("Can't create the unchecked book '{}' in database! " +
                    "Class PostgreDBService, method createUncheckedBook().", bookName, e);
        }
        return null;
    }

    @Override
    public int getIDOfUncheckedBook(int userID, String bookName, String author, String description) {
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT id FROM uncheckedbook" +
                             " WHERE userid=? AND bookname=? AND author=? AND description=?")) {
            stmt.setInt(1, userID);
            stmt.setString(2, bookName);
            stmt.setString(3, author);
            stmt.setString(4, description);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't get id of unchecked book {} {} {} {} from database! " +
                            "Class PostgreDBService, method getAuthorByFullName().",
                    userID, bookName, author, description, e);
        }
        return 0;
    }

    @Override
    public List<Book> searchBooksByRequest(String sqlString, String... parameters) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionSettings.getUrl(),
                connectionSettings.getUsername(), connectionSettings.getPassword());
             PreparedStatement stmt = connection.prepareStatement(sqlString)) {
            for (int i = 0; i < parameters.length; i++) {
                stmt.setString(i + 1, parameters[i]);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Author author = getAuthorByID(rs.getInt(3));
                    Book book = new Book(rs.getInt(1), rs.getString(2), author,
                            new Genre(rs.getString(4)),
                            new Category(rs.getString(5)),
                            rs.getInt(6), rs.getString(7));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't search books by request '{}' from database! " +
                            "Class PostgreDBService, method searchBooksByRequest().",
                    sqlString, Arrays.asList(parameters), e);
        }
        return books;
    }
}
