package rs.raf.repositories.IRepos;

import rs.raf.models.User;

import java.util.List;

public interface UserRepository {
    public User addUser(User user);
    public User findUserById(Integer id);
    public User findUserByEmail(String email);
    public User editUser(User user);
    public List<User> listAllUsers();
}
