package library.test;

import library.model.User;
import library.repository.*;
import library.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Solution {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
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
        if (!userRepository.existsByLogin(user.getLogin())) {
            System.out.println("Создаю!!!! " + user.getLogin());
            user = userRepository.save(user);
            System.out.println("--------------------------\nsave(user):\n");

            System.out.println("--------------------------\nfindAllUsers():\n" +
                    userRepository.findAll());

            System.out.println("--------------------------\nsave(user)  --- update I hope:\n");
            user.setFirstName("New Hah name");
            userRepository.save(user);
        }

        System.out.println("--------------------------\nfindAllUsers():\n" +
                userRepository.findAll());
        System.out.println("--------------------------\ndeleteUserByLogin " + user.getLogin());
        userService.deleteByLogin(user.getLogin());

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
