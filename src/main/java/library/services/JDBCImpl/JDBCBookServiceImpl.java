package library.services.JDBCImpl;

import library.config.LibrarySettings;
import library.model.*;
import library.services.interfaces.BookService;
import library.services.interfaces.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

public class JDBCBookServiceImpl implements BookService {
    private List<Genre> genres;
    private List<Category> categories;
    private Set<UncheckedBook> uncheckedBookSet;

    private DBService dbService;
    private LibrarySettings librarySettings;

    private static final Logger LOG = LoggerFactory.getLogger(JDBCBookServiceImpl.class.getName());

    public JDBCBookServiceImpl(DBService dbService, LibrarySettings librarySettings) {
        this.dbService = dbService;
        this.librarySettings = librarySettings;
    }

    @PostConstruct
    private void loadBooksAndAuthors() {
        getAllGenres();
        getAllCategories();
        getAllUncheckedBooks();
    }

    @Override
    public List<Book> getAllBooks() {
        return dbService.getAllBooks();
    }

    @Override
    public List<Genre> getAllGenres() {
        if (genres == null) {
            synchronized (this) {
                genres = dbService.getAllGenres();
                if (genres == null) {
                    genres = new ArrayList<>();
                }
            }
        }
        return genres;
    }

    @Override
    public List<Category> getAllCategories() {
        if (categories == null) {
            synchronized (this) {
                categories = dbService.getAllCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
            }
        }
        return categories;
    }

    @Override
    public Set<UncheckedBook> getAllUncheckedBooks() {
        if (uncheckedBookSet == null) {
            synchronized (this) {
                uncheckedBookSet = dbService.getAllUncheckedBooks();
                if (uncheckedBookSet == null) {
                    uncheckedBookSet = new HashSet<>();
                }
            }
        }
        return uncheckedBookSet;
    }

    @Override
    public Author getAuthorByID(int id) {
        return dbService.getAuthorByID(id);
    }

    @Override
    public Author getAuthorByFullName(String firstName, String secondName, String lastName) {
        return dbService.getAuthorByFullName(firstName, secondName, lastName);
    }

    @Override
    public Author addNewAuthor(String firstName, String secondName, String lastName) {
        dbService.createAuthor(firstName, secondName, lastName, 0);
        Author author = dbService.getAuthorByFullName(firstName, secondName, lastName);
        return author;
    }

    @Override
    public Boolean isGenreExist(Genre genre) {
        return genres.contains(genre);
    }

    @Override
    synchronized public Boolean addNewBook(String bookName, String authorFirstName, String authorSecondName,
                                           String authorLastName, Genre genre, Category category,
                                           int popularity, String description) {
        Author author = getAuthorByFullName(authorFirstName, authorSecondName, authorLastName);
        if (author == null) {
            author = addNewAuthor(authorFirstName, authorSecondName, authorLastName);
        }
        int authorID = author.getId();
        int bookID = dbService.getBookIDByNameAndAuthor(bookName, authorID);
        if (bookID != 0) {
            LOG.info("Can't create the book {}!", bookName);
            return false;
        }

        if (!isGenreExist(genre)) {
            dbService.createGenre(genre);
        }
        dbService.createBook(bookName, authorID, genre, category, popularity, description);
        dbService.getBookIDByNameAndAuthor(bookName, authorID);
        return true;
    }

    @Override
    public void deleteBook(Book book) {
        dbService.deleteBook(book.getId());
    }

    @Override
    public Book getBookByID(int id) {
        return dbService.getBookByID(id);
    }

    @Override
    public UncheckedBook getUncheckedBookById(int id) {
        Optional<UncheckedBook> bookOptional = getAllUncheckedBooks().stream()
                .filter(b -> b.getId() == id).findFirst();
        return bookOptional.orElse(null);
    }

    @Override
    public void addNewUncheckedBook(User user, String bookName,
                                    String author, String description) {
        UncheckedBook uncheckedBook = dbService.createUncheckedBook(user.getId(),
                bookName, author, description);
        uncheckedBookSet.add(uncheckedBook);
    }

    @Override
    public void refuseUncheckedBook(UncheckedBook uncheckedBook) {
        String uncheckedBookName = uncheckedBook.getBookName();
        String fullPath = librarySettings.getUncheckedPath();
        fullPath += uncheckedBookName;
        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }

        dbService.deleteUncheckedBook(uncheckedBook.getId());
        uncheckedBookSet.remove(uncheckedBook);
    }

    @Override
    public void acceptUncheckedBook(UncheckedBook uncheckedBook, Book newBook) {
        String uncheckedBookName = uncheckedBook.getBookName();
        String oldFullName = librarySettings.getUncheckedPath() + uncheckedBookName;
        File oldFile = new File(oldFullName);
        if (oldFile.exists()) {
            newBook.setName(newBook.getName() + "." + getFileExtension(oldFile.getName()));
            addNewBook(newBook.getName(), newBook.getAuthor().getFirstName(),
                    newBook.getAuthor().getSecondName(), newBook.getAuthor().getLastName(),
                    newBook.getGenre(), newBook.getCategory(), 0, newBook.getDescription());

            String newFullName = librarySettings.getBooksPath() + newBook.getAuthor() + " " + newBook.getName();
            oldFile.renameTo(new File(newFullName));

            dbService.deleteUncheckedBook(uncheckedBook.getId());
            uncheckedBookSet.remove(uncheckedBook);
        }
    }
}
