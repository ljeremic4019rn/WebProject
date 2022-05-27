package rs.raf.repositories.SQLRepos;

import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.models.User;
import rs.raf.repositories.IRepos.UserRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlUserRepository extends MySqlAbstractRepository implements UserRepository {


    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public User findUserById(Integer id) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User editUser(User user) {
        return null;
    }

    @Override
    public List<User> allUsers() {
        return null;
    }
}
