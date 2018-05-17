package library.test;

import library.model.User;
import library.repository.UserRepository;
import library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Solution {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void start() {
        System.out.println("--------------------------\nfindAll():\n" +
                userRepository.findAll());
        User user = new User("Hahahahahaha", "Hah", "Hahallo", "hash(123)", "user");
        System.out.println("--------------------------\nsave(user):\n" +
                userRepository.save(user));
        System.out.println("--------------------------\nfindAll():\n" +
                userRepository.findAll());
    }
}
