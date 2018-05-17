package library.services;

import library.model.User;
import library.repository.UserRepository;

public class SpringDataUserServiceImpl implements UserService {
    private UserRepository userRepository;

    public SpringDataUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean isLoginExist(String login) {
        return null;
    }

    @Override
    public Boolean register(String login, String firstName, String lastName, String passwordHash, String role) {
        return null;
    }

    @Override
    public void delete(User u) {
        userRepository.deleteById(u.getId());
    }

    @Override
    public User getUserByLogin(String login) {
        return null;
    }

    @Override
    public void updateFirstName(int id, String newFirstName) {

    }

    @Override
    public void updateLastName(int id, String newLastName) {

    }

    @Override
    public void updatePassword(int id, String newPasswordHash) {

    }
}
