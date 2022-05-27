package rs.raf.repositories.SQLRepos;


import rs.raf.models.Tag;
import rs.raf.repositories.IRepos.TagRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Tag> tags = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM tag"
            );
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Tag toAdd = new Tag();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setName(resultSet.getString("tag_name"));

                tags.add(toAdd);
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Integer> tagIds = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT tag_id FROM tag_news_article WHERE article_id = ?"
            );
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tagIds.add(resultSet.getInt("tag_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return tagIds;
    }
}
