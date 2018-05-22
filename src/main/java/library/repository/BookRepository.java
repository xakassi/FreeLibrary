package library.repository;

import library.model.Author;
import library.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    boolean existsByNameAndAuthor(String name, Author author);

    Book getBookById(int id);
}
