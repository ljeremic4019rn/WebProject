package rs.raf.repositories.SQLRepos;


import rs.raf.models.Article;
import rs.raf.models.Tag;
import rs.raf.repositories.IRepos.TagRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlTagRepository extends MySqlAbstractRepository implements TagRepository {

    @Override
    public Tag addTag(Tag tag) {

        List<Tag> tags = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String[] generatedColumns = {"id"};
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM tags WHERE name = ?");
            preparedStatement.setString(1, tag.getName());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tag.setId(resultSet.getInt("id"));
                return tag;
            }

            preparedStatement = connection.prepareStatement("INSERT INTO tags (name) VALUES(?)", generatedColumns);
            preparedStatement.setString(1, tag.getName());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) tag.setId(resultSet.getInt(1));

//         String date = "01/05/2022"

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return tag;
    }

    @Override
    public List<Tag> allTags() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Tag> tags = new ArrayList<>();

        try {
            connection = newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tags");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tags.add(new Tag(
                        resultSet.getInt("id"),
                        resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return tags;
    }

    @Override
    public List<Tag> tagsFromArticle(Integer articleId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Integer> tagIds = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM tags_articles WHERE articleId = ?");
            preparedStatement.setInt(1, articleId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tagIds.add(resultSet.getInt("tagId"));
            }

            for (Integer tagId: tagIds) {
                preparedStatement = connection.prepareStatement("SELECT * FROM tags WHERE id = ?");
                preparedStatement.setInt(1, tagId);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    tags.add(new Tag(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }
        return tags;
    }

    @Override
    public void addTagsArticles(Integer tagId, Integer articleId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("SELECT * FROM tags_articles WHERE tagId = ? AND articleId = ?");
            preparedStatement.setInt(1, tagId);
            preparedStatement.setInt(2, articleId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return;
            }

            preparedStatement = connection.prepareStatement("INSERT INTO tags_articles (articleId, tagId) VALUES(?,?)", generatedColumns);
            preparedStatement.setInt(1, articleId);
            preparedStatement.setInt(2, tagId);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }

}
