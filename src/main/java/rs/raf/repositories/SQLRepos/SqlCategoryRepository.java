package rs.raf.repositories.SQLRepos;


import rs.raf.models.Category;
import rs.raf.repositories.IRepos.CategoryRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {

    @Override
    public Category addCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();

            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO category (category_name, cat_description) VALUES (?, ?)",
                    generatedColumns
            );
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                category.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return category;
    }

    @Override
    public boolean deleteCategory(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM article WHERE category_id = ?"
            );
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                closeStatement(preparedStatement);
                closeResultSet(resultSet);
                closeConnection(connection);
                return false;
            }

            preparedStatement = connection.prepareStatement(
                    "DELETE FROM category WHERE id = ?"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return true;
    }

    @Override
    public Category updateCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE category SET category_name = ?, cat_description = ? WHERE id = ?"
            );
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, category.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }

        return category;
    }

    @Override
    public List<Category> allCategories() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Category> categories = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM category"
            );
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Category toAdd = new Category();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setName(resultSet.getString("category_name"));
                toAdd.setDescription(resultSet.getString("cat_description"));

                categories.add(toAdd);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return categories;
    }

    @Override
    public Category getSingleCategory(Integer id) {
        return null;
    }
}
