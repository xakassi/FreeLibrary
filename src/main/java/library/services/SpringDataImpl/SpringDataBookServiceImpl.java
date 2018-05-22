package library.services.SpringDataImpl;

import library.config.LibrarySettings;
import library.model.*;
import library.repository.*;
import library.services.additional.IterableToCollectionMaker;
import library.services.interfaces.BookService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.util.Set;

public class SpringDataBookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private CategoryRepository categoryRepository;
    private UncheckedBookRepository uncheckedBookRepository;
    private AuthorRepository authorRepository;
    private LibrarySettings librarySettings;

    public SpringDataBookServiceImpl(BookRepository bookRepository,
                                     GenreRepository genreRepository,
                                     CategoryRepository categoryRepository,
                                     UncheckedBookRepository uncheckedBookRepository,
                                     AuthorRepository authorRepository,
                                     LibrarySettings librarySettings) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.categoryRepository = categoryRepository;
        this.uncheckedBookRepository = uncheckedBookRepository;
        this.authorRepository = authorRepository;
        this.librarySettings = librarySettings;
    }

    @PostConstruct
    private void loadBooksAndAuthors() {
        getAllUncheckedBooks();
    }

    @Override
    public List<Book> getAllBooks() {
        return IterableToCollectionMaker.iterableToList(bookRepository.findAll());
    }

    @Override
    public List<Genre> getAllGenres() {
        return IterableToCollectionMaker.iterableToList(genreRepository.findAll());
    }

    @Override
    public List<Category> getAllCategories() {
        return IterableToCollectionMaker.iterableToList(categoryRepository.findAll());
    }

    @Override
    public Set<UncheckedBook> getAllUncheckedBooks() {

        return IterableToCollectionMaker.iterableToSet(uncheckedBookRepository.findAll());

    }

    @Override
    public Author getAuthorByID(int id) {
        return authorRepository.getAuthorById(id);
    }

    @Override
    public Author getAuthorByFullName(String firstName, String secondName, String lastName) {
        return authorRepository.getAuthorByFirstNameAndSecondNameAndLastName(firstName,
                secondName, lastName);
    }

    @Override
    public Author addNewAuthor(String firstName, String secondName, String lastName) {
        Author author = new Author(firstName, secondName, lastName);
        return authorRepository.save(author);
    }

    @Override
    public Boolean isGenreExist(Genre genre) {
        return genreRepository.existsById(genre.getName());
    }

    @Override
    public Boolean addNewBook(String bookName, String authorFirstName,
                              String authorSecondName, String authorLastName,
                              Genre genre, Category category, int popularity,
                              String description) {
        Author author = authorRepository.getAuthorByFirstNameAndSecondNameAndLastName(
                authorFirstName, authorSecondName, authorLastName);
        if (author == null) {
            author = new Author(authorFirstName, authorSecondName, authorLastName);
            author = authorRepository.save(author);
        }
        if (bookRepository.existsByNameAndAuthor(bookName, author)) {
            return false;
        }
        Book book = new Book(bookName, author, genre, category, 0, "");
        bookRepository.save(book);
        return true;
    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Book getBookByID(int id) {
        return bookRepository.getBookById(id);
    }

    @Override
    public UncheckedBook getUncheckedBookById(int id) {
        return uncheckedBookRepository.getUncheckedBookById(id);
    }

    @Override
    public void addNewUncheckedBook(User user, String bookName,
                                    String author, String description) {
        UncheckedBook uncheckedBook = new UncheckedBook(user, bookName, author,
                description);
        uncheckedBook = uncheckedBookRepository.save(uncheckedBook);
    }

    private void deleteUncheckedBookFile(UncheckedBook uncheckedBook) {
        String uncheckedBookName = uncheckedBook.getBookName();
        String fullPath = librarySettings.getUncheckedPath();
        fullPath += uncheckedBookName;
        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public void refuseUncheckedBook(UncheckedBook uncheckedBook) {
        deleteUncheckedBookFile(uncheckedBook);
        uncheckedBookRepository.delete(uncheckedBook);
    }

    @Override
    public void acceptUncheckedBook(UncheckedBook uncheckedBook, Book newBook) {
        String uncheckedBookName = uncheckedBook.getBookName();
        String oldFullName = librarySettings.getUncheckedPath() + uncheckedBookName;
        File oldFile = new File(oldFullName);
        if (oldFile.exists()) {
            newBook.setName(newBook.getName() + "." +
                    getFileExtension(oldFile.getName()));
            addNewBook(newBook.getName(), newBook.getAuthor().getFirstName(),
                    newBook.getAuthor().getSecondName(),
                    newBook.getAuthor().getLastName(),
                    newBook.getGenre(),
                    newBook.getCategory(), 0, newBook.getDescription());

            String newFullName = librarySettings.getBooksPath() +
                    newBook.getAuthor() + " " + newBook.getName();
            oldFile.renameTo(new File(newFullName));

            uncheckedBookRepository.delete(uncheckedBook);
        }
    }
}
