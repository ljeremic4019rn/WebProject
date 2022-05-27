package rs.raf.services;


import rs.raf.models.User;
import rs.raf.repositories.IRepos.UserRepository;

import javax.inject.Inject;
import java.util.List;

public class UserService {

    @Inject
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    public User findUser(Integer id) {
        return userRepository.findUserById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> allUsers() {
        return userRepository.allUsers();
    }

    public User editUser(User user) {
        return userRepository.editUser(user);
    }


}
