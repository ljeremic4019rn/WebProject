package rs.raf.services;


import rs.raf.models.Comment;
import rs.raf.repositories.IRepos.CommentRepository;
import rs.raf.repositories.SQLRepos.SqlCommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return commentRepository.addComment(comment);
    }

    public List<Comment> getCommentsForArticle(Integer id) {
        return commentRepository.getCommentsForArticle(id);
    };

}
