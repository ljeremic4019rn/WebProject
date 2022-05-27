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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE email = ?"
            );
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                closeResultSet(resultSet);
                closeStatement(preparedStatement);
                closeConnection(connection);
                return null;
            }

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO user (email, name, surname, password, role, status) VALUES (?, ?, ?, ?, ?, ?)",
                    generatedColumns
            );
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, DigestUtils.sha256Hex(user.getPassword()));
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setString(6, user.getStatus());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }

        return user;
    }

    @Override
    public User findUserById(Integer id) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT id, email, name, surname, role, status FROM user WHERE id = ?"
            );
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setLastname(resultSet.getString("surname"));
                user.setStatus(resultSet.getString("status"));
                user.setRole(resultSet.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return user;
    }

    @Override
    public User editUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        System.out.println(user.getId() + " " + user.getName() + " " + user.getLastname() + " " + user.getEmail()
             + " " + user.getRole());

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE email = ?"
            );
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("id") != user.getId())
                    return null;
            }

            preparedStatement = connection.prepareStatement(
                    "UPDATE user " +
                            "SET name = ?, surname = ?, email = ?, role = ?, status = ? " +
                            "WHERE id = ?"
            );
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getStatus());
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }

        return user;
    }

    @Override
    public List<User> listAllUsers() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<User> users = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT id, email, name, surname, role, status FROM user"
            );
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User toAdd = new User();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setEmail(resultSet.getString("email"));
                toAdd.setName(resultSet.getString("name"));
                toAdd.setLastname(resultSet.getString("surname"));
                toAdd.setRole(resultSet.getString("role"));
                toAdd.setStatus(resultSet.getString("status"));

                users.add(toAdd);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return users;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE email = ?"
            );
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setLastname(resultSet.getString("surname"));
                user.setStatus(resultSet.getString("status"));
                user.setRole(resultSet.getString("role"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return user;
    }
}
