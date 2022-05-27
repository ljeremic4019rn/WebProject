package rs.raf.repositories.IRepos;


import rs.raf.models.Comment;

import java.util.List;

public interface CommentRepository {
    public Comment addComment(Comment comment);
    public List<Comment> getCommentsForArticle(Integer id);
}
