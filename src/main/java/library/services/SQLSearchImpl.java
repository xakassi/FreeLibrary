package library.services;

import library.model.Book;
import library.model.BookSearchRequest;

import java.util.ArrayList;
import java.util.List;

public class SQLSearchImpl implements SearchService {
    private DBService dbService;

    public SQLSearchImpl(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public List<Book> searchBooks(BookSearchRequest bookSearchRequest) {
        String searchingStringForDB = "SELECT id, name, authorid, genreid, categoryid, " +
                "popularity, description FROM book WHERE ";

        List<String> parametersNames = new ArrayList<>();
        List<String> parametersValues = new ArrayList<>();
        String bookName = bookSearchRequest.getName().trim().toLowerCase();
        String author = bookSearchRequest.getAuthor().trim().toLowerCase();
        if (!bookName.equals("")) {
            String nameParameter = "(lower(name) LIKE ?)";
            parametersNames.add(nameParameter);
            parametersValues.add("%" + bookName + "%");
        }
        if (!author.equals("")) {
            String authorParameter =
                    "(authorid IN (SELECT id FROM author WHERE lower(lastname) LIKE ?))";
            parametersNames.add(authorParameter);
            parametersValues.add("%" + author + "%");
        }
        if (!bookSearchRequest.getGenre().equals("Choose a genre")) {
            String genreParameter = "(genreid=?)";
            parametersNames.add(genreParameter);
            parametersValues.add(bookSearchRequest.getGenre());
        }
        if (!bookSearchRequest.getCategory().equals("Choose a category")) {
            String categoryParameter = "(categoryid=?)";
            parametersNames.add(categoryParameter);
            parametersValues.add(bookSearchRequest.getCategory());
        }

        if (parametersNames.isEmpty()) {
            return new ArrayList<>();
        }

        int paramCount = parametersValues.size();
        String[] paramValues = new String[paramCount];
        paramValues = parametersValues.toArray(paramValues);
        for (int i = 0; i < paramCount - 1; i++) {
            searchingStringForDB += parametersNames.get(i) + " AND ";
        }
        searchingStringForDB += parametersNames.get(paramCount - 1);

        return dbService.searchBooksByRequest(searchingStringForDB, paramValues);
    }
}
