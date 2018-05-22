package library.repository;

import library.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {
    Author getAuthorById(int id);
    Author getAuthorByFirstNameAndSecondNameAndLastName(String firstName,
                                                        String secondName, String lastName);
}
