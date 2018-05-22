package library.services.SpringDataImpl;

import library.model.Book;
import library.model.BookSearchRequest;
import library.services.interfaces.SearchService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringDataSearchService implements SearchService {
    @PersistenceContext
    private EntityManager em;

    private List<Book> sqlSearch(String query, Map<String, String> parameters) {
        Query q = em.createQuery(query,
                Book.class);
        for (String parameterName : parameters.keySet()) {
            q.setParameter(parameterName, parameters.get(parameterName));
        }
        return q.getResultList();
    }

    @Override
    public List<Book> searchBooks(BookSearchRequest bookSearchRequest) {
        StringBuilder searchingStringForDB = new StringBuilder("SELECT b FROM Book b WHERE ");

        List<String> parametersNames = new ArrayList<>();
        Map<String, String> parametersValues = new HashMap<>();
        String bookName = bookSearchRequest.getName().trim().toLowerCase();
        String author = bookSearchRequest.getAuthor().trim().toLowerCase();
        if (!bookName.equals("")) {
            String nameParameter = "(lower(b.name) LIKE :name)";
            parametersNames.add(nameParameter);
            parametersValues.put("name", "%" + bookName + "%");
        }
        if (!author.equals("")) {
            String authorParameter =
                    "(lower(b.author.lastName) LIKE :lastName)";
            parametersNames.add(authorParameter);
            parametersValues.put("lastName", "%" + author + "%");
        }
        if (!bookSearchRequest.getGenre().equals("Choose a genre")) {
            String genreParameter = "(b.genre.name = :genre)";
            parametersNames.add(genreParameter);
            parametersValues.put("genre", bookSearchRequest.getGenre());
        }
        if (!bookSearchRequest.getCategory().equals("Choose a category")) {
            String categoryParameter = "(b.category.name = :category)";
            parametersNames.add(categoryParameter);
            parametersValues.put("category", bookSearchRequest.getCategory());
        }

        if (parametersNames.isEmpty()) {
            return new ArrayList<>();
        }

        int paramCount = parametersValues.size();
        for (int i = 0; i < paramCount - 1; i++) {
            searchingStringForDB.append(parametersNames.get(i)).append(" AND ");
        }
        searchingStringForDB.append(parametersNames.get(paramCount - 1));

        return sqlSearch(searchingStringForDB.toString(), parametersValues);
    }
}
