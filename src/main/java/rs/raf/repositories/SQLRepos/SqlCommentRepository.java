package rs.raf.repositories.SQLRepos;


import rs.raf.models.Comment;
import rs.raf.repositories.IRepos.CommentRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {

    @Override
    public Comment addComment(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO comments (articleId, authorName, content, publishedDate) VALUES(?, ?, ?, CURRENT_TIMESTAMP())",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, comment.getArticleId());
            preparedStatement.setString(2, comment.getContent());
            preparedStatement.setString(3, comment.getAuthorName());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }

    @Override
    public List<Comment> findCommentsForArticle(Integer id) {
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM comments WHERE articleId = ? ORDER BY publishedDate DESC");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                comments.add(new Comment(
                        resultSet.getInt("id"),
                        resultSet.getInt("articleId"),
                        resultSet.getString("authorName"),
                        resultSet.getString("content"),
                        resultSet.getDate("publishedDate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return comments;
    }
}
