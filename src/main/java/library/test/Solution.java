package library.test;

import library.model.User;
import library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Solution {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @PostConstruct
    public void start() {
        System.out.println("--------------------------\nfindAllUsers():\n" +
                userRepository.findAll());
        User user = new User("Hahahahahaha", "Hah", "Hahallo", "hash(123)", "user");
        System.out.println("--------------------------\nsave(user):\n" +
                userRepository.save(user));
        System.out.println("--------------------------\nfindAllUsers():\n" +
                userRepository.findAll());
        System.out.println("--------------------------\ndeleteUserByLogin");
        //userRepository.deleteUserByLogin("Hahahahahaha");
        System.out.println("--------------------------\nfindAllUsers():\n" +
                userRepository.findAll());

        System.out.println("--------------------------\nfindAllGenre():\n" +
                genreRepository.findAll());
        System.out.println("--------------------------\nfindAllCategories():\n" +
                categoryRepository.findAll());
        System.out.println("--------------------------\nfindAllAuthors():\n" +
                authorRepository.findAll());
        System.out.println("--------------------------\nfindAllBooks():\n" +
                bookRepository.findAll());
    }
}
