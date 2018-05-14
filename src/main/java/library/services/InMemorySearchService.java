package library.services;

import library.model.Book;
import library.model.BookSearchRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemorySearchService implements SearchService {
    private BookService bookService;

    public InMemorySearchService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public List<Book> searchBooks(BookSearchRequest bookSearchRequest) {
        List<Book> books = bookService.getAllBooks();
        Boolean wasAnythingSearched = false;

        String bookName = bookSearchRequest.getName().trim().toLowerCase();
        String author = bookSearchRequest.getAuthor().trim().toLowerCase();
        if (!bookName.equals("")) {
            wasAnythingSearched = true;
            books = books.stream()
                    .filter(b -> b.getName().toLowerCase().
                            contains(bookName.toLowerCase()) ||
                            bookName.toLowerCase().contains
                                    (getNameWithoutExtension(b.getName().toLowerCase())))
                    .collect(Collectors.toList());
        }
        if (!author.equals("")) {
            wasAnythingSearched = true;
            books = books.stream()
                    .filter(b -> b.getAuthor().getLastName().toLowerCase()
                            .contains(author.toLowerCase()) || author.toLowerCase()
                            .contains(b.getAuthor().getLastName().toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (!bookSearchRequest.getGenre().equals("Choose a genre")) {
            wasAnythingSearched = true;
            books = books.stream()
                    .filter(b -> b.getGenre().equals(bookSearchRequest.getGenre()))
                    .collect(Collectors.toList());
        }
        if (!bookSearchRequest.getCategory().equals("Choose a category")) {
            wasAnythingSearched = true;
            books = books.stream()
                    .filter(b -> b.getCategory().equals(bookSearchRequest.getCategory()))
                    .collect(Collectors.toList());
        }

        if (!wasAnythingSearched) {
            return new ArrayList<>();
        }

        return books;

    }

    private String getNameWithoutExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

    public List<Book> searchNFirstBooksByPopularity(List<Book> books, int n) {
        List<Book> sortedByPopularity = new ArrayList<>(books);
        sortedByPopularity.sort(Book::sortByPopularity);

        List<Book> mostPopularBooks = new ArrayList<>();
        for (int i = 0; i < sortedByPopularity.size() && n > 0; i++) {
            mostPopularBooks.add(sortedByPopularity.get(i));
            n--;
        }
        return mostPopularBooks;
    }
}
