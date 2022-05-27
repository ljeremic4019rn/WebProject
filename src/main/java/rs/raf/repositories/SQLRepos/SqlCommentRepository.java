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
        return null;
    }

    @Override
    public List<Comment> findCommentsForArticle(Integer id) {
        return null;
    }

    @Override
    public List<Comment> allComments() {
        return null;
    }
}
