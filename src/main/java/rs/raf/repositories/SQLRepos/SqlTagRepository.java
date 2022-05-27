package rs.raf.repositories.SQLRepos;


import rs.raf.models.Tag;
import rs.raf.repositories.IRepos.TagRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlTagRepository extends MySqlAbstractRepository implements TagRepository {

    @Override
    public Tag addTag(Tag tag) {
        return null;
    }

    @Override
    public List<Tag> allTags() {

        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;

        List<Tag> tags = new ArrayList<>();

        try {
            connection = newConnection();
            preparedStatement = connection.createStatement();
            resultSet = preparedStatement.executeQuery("SELECT * FROM tag");

            while (resultSet.next()) {
                tags.add(new Tag(resultSet.getInt("id"), resultSet.getString("tag_name")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return tags;
    }

    @Override
    public List<Integer> tagsForPost(Integer id) {
        return null;
    }
}
