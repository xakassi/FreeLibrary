package library.repository;

import library.model.UncheckedBook;
import library.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UncheckedBookRepository extends
        CrudRepository<UncheckedBook, Integer> {
    UncheckedBook getUncheckedBookById(int id);

    void deleteByUser(User user);
    List<UncheckedBook> getUncheckedBooksByUser(User user);
}
