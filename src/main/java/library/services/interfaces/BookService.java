package library.services.interfaces;

import library.model.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface BookService {
    HashSet<String> validExtensions = new HashSet<>(Arrays.asList("txt", "doc", "docx",
            "pdf", "epub", "fb2", "djvu", "rtf"));

    /**
     * Get all books
     *
     * @return list of all books in library
     */
    List<Book> getAllBooks();

    /**
     * Get all authors
     *
     * @return list of all authors in library
     */
    //List<Author> getAllAuthors();

    /**
     * Get all genres of books
     *
     * @return list of all genres in library
     */
    List<Genre> getAllGenres();

    /**
     * Get all categories of books
     *
     * @return list of all categories in library
     */
    List<Category> getAllCategories();

    /**
     * Get set of all unchecked books
     *
     * @return set of unchecked books
     */
    Set<UncheckedBook> getAllUncheckedBooks();

    /**
     * Get author by his id
     *
     * @param id is the id of searching author
     * @return author with id {@code id}
     */
    Author getAuthorByID(int id);

    /**
     * Get author by his full name
     *
     * @param firstName  is the name of author
     * @param secondName - his patronymic
     * @param lastName   - his surname
     * @return author with this full name
     */
    Author getAuthorByFullName(String firstName, String secondName, String lastName);

    /**
     * Register a new author in library
     *
     * @param firstName  is the name of author
     * @param secondName - his patronymic
     * @param lastName   - his surname
     * @return creating author
     */
    Author addNewAuthor(String firstName, String secondName, String lastName);

    /**
     * Check is genre {@code genre} is register in the library or not
     *
     * @param genre is the searching genre
     * @return {@code true} if tge genre exist in the library
     */
    Boolean isGenreExist(Genre genre);

    /**
     * Add a new book with following parameters in the library
     *
     * @param bookName         is the name of the book
     * @param authorFirstName  is the author name
     * @param authorSecondName is the author patronymic
     * @param authorLastName   is the author surname
     * @param genre            is the genre of the book
     * @param category         is the category of the book
     * @param popularity       is the popularity level of the book
     * @param description      is the description for the book
     * @return {@code true} if the book was successfully added
     */
    Boolean addNewBook(String bookName, String authorFirstName, String authorSecondName,
                       String authorLastName, Genre genre, Category category,
                       int popularity, String description);

    /**
     * Delete the book {@code book} from the library
     *
     * @param book is the deleting book
     */
    void deleteBook(Book book);

    /**
     * Get book by its id {@code id}
     *
     * @param id is the id of the searching book
     * @return book with this id
     */
    Book getBookByID(int id);

    /**
     * * Take a book from set of unchecked books.
     * Administrator should process it and decide to add it to the library or not.
     *
     * @param id is the id of taken book
     * @return unchecked book from set
     */
    UncheckedBook getUncheckedBookById(int id);

    /**
     * Add unchecked book with following parameters to the queue of unchecked books
     *
     * @param user        is an user who offers this book to the library
     * @param bookName    is a book name given it by the user
     * @param author      is the author surname or full name written by user
     * @param description is the description for book given by user
     */
    void addNewUncheckedBook(User user, String bookName, String author, String description);

    /**
     * Delete unchecked book
     * from the folder for unchecked books (see {@code application.context})
     * and from the set of unchecked books
     *
     * @param uncheckedBook is the refused book
     */
    void refuseUncheckedBook(UncheckedBook uncheckedBook);

    /**
     * Add new book {@code newBook} to the library (database and list of books),
     * delete unchecked book from set of unchecked books
     * and move file {@code uncheckedBookName} from unchecked books folder
     * to the library books folder (see this folders in {@code application.properties})
     * and rename it to "{author} {name}"
     *
     * @param uncheckedBook is the name for searching file in unchecked books folder
     * @param newBook       is the book for this file
     */
    void acceptUncheckedBook(UncheckedBook uncheckedBook, Book newBook);

    /**
     * Check an extension of the file,
     * it should be text file (some most popular formats)
     *
     * @param fileName is the name of file for checking
     * @return {@code true} if the file
     * has an text extension, else {@code false}
     */
    default Boolean isExtensionValid(String fileName) {
        return validExtensions.contains(getFileExtension(fileName));
    }

    default String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1
                && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }

}
