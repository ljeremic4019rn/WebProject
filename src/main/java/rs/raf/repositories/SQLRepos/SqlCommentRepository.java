package rs.raf.repositories.SQLRepos;


import rs.raf.models.Comment;
import rs.raf.repositories.IRepos.CommentRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

            preparedStatement = connection.prepareStatement("INSERT INTO comments (postId, author, text, date) VALUES(?, ?, ?, CURRENT_TIMESTAMP())", generatedColumns);
            preparedStatement.setInt(1, comment.getPostId());
            preparedStatement.setString(1, comment.getAuthorName());
            preparedStatement.setString(1, comment.getContent());
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
            preparedStatement = connection.prepareStatement("SELECT * FROM comments WHERE postId = ? ORDER BY date DESC");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                comments.add(new Comment(resultSet.getInt("id"), resultSet.getInt("postId"), resultSet.getString("author"),
                        resultSet.getString("text"), resultSet.getDate("date")));
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
