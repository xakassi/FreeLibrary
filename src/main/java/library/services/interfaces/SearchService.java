package library.services.interfaces;

import library.model.Book;
import library.model.BookSearchRequest;

import java.util.List;

public interface SearchService {
    /**
     * Search books in database by parameters from the request
     * (book name, author surname, genre category)
     *
     * @param bookSearchRequest is the entity which holds the parameters
     *                          for filtering and searching
     * @return list of books with these parameters
     */
    List<Book> searchBooks(BookSearchRequest bookSearchRequest);
}
