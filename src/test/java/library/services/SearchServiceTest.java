package library.services;

import library.model.Author;
import library.model.Book;
import library.model.BookSearchRequest;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.*;

import java.util.*;

public class SearchServiceTest {
    private Author[] testAuthors;
    private Book[] testBooks;

    {
        testAuthors = new Author[]{new Author(1, "Фёдор", "Михайлович", "Достоевский", 19),
                new Author(2, "Виктор", "", "Гюго", 19),
                new Author(3, "Джоан", "", "Роулинг", 21)};

        testBooks = new Book[]{new Book(1, "Братья Карамазовы", testAuthors[0],
                "classic", "adults", 5, ""),
                new Book(2, "Идиот", testAuthors[0],
                        "classic", "adults", 9, ""),
                new Book(3, "Отверженные", testAuthors[1],
                        "classic", "adults", 6, ""),
                new Book(4, "Гарри Поттер и Философский камень", testAuthors[2],
                        "fantasy", "teenagers", 15, "")};
    }

    private SearchService searchService;
    private Map<BookSearchRequest, List<Book>> requestsAndExpectedValues;

    private static Mockery mockery = new Mockery();

    @Before
    public void initTestLibraryContent() {
        List<Book> books = Arrays.asList(testBooks);

        BookService bookService = mockery.mock(BookService.class);
        mockery.checking(new Expectations() {
            {
                allowing(bookService).getAllBooks();
                will(returnValue(books));
            }
        });

        searchService = new InMemorySearchService(bookService);
    }

    @Before
    public void initRequestsForSearchAndExpectedValues() {
        requestsAndExpectedValues = new HashMap<>();

        BookSearchRequest bookSearchRequest = new BookSearchRequest("", "",
                "Choose a genre", "Choose a category");
        requestsAndExpectedValues.put(bookSearchRequest, new ArrayList<>());

        bookSearchRequest = new BookSearchRequest("", "Достоевский",
                "Choose a genre", "Choose a category");
        requestsAndExpectedValues.put(bookSearchRequest, Arrays.asList(testBooks[0], testBooks[1]));

        bookSearchRequest = new BookSearchRequest("", "",
                "classic", "Choose a category");
        requestsAndExpectedValues.put(bookSearchRequest, Arrays.asList(testBooks[0], testBooks[1],
                testBooks[2]));

        bookSearchRequest = new BookSearchRequest("", "",
                "Choose a genre", "teenagers");
        requestsAndExpectedValues.put(bookSearchRequest, Arrays.asList(testBooks[3]));
    }

    @After
    public void clearRequestsAndExpectations(){
        requestsAndExpectedValues.clear();
    }


    @Test
    public void searchTest() {
        for (BookSearchRequest bookSearchRequest : requestsAndExpectedValues.keySet()) {
            List<Book> actualSearchingBooks = searchService.searchBooks(bookSearchRequest);
            List<Book> expectedSearchingBooks = requestsAndExpectedValues.get(bookSearchRequest);
            Assert.assertArrayEquals(actualSearchingBooks.toArray(), expectedSearchingBooks.toArray());
        }
    }
}
