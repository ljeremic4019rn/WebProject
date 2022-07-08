package rs.raf.repositories.IRepos;

import rs.raf.models.User;

import java.util.List;

public interface UserRepository {
    public User addUser(User user);
    public User findUserById(Integer id);
    public User findUserByEmail(String email);
    public User editUser(User user);
    public List<User> allUsers();
    public void deleteUser(Integer id);
    public void activateUser(Integer id);
    public void deactivateUser(Integer id);
    public List<User> usersByPage(Integer pageNum);
}
