package rs.raf.services;


import rs.raf.models.Comment;
import rs.raf.repositories.IRepos.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return commentRepository.addComment(comment);
    }

    public List<Comment> findCommentsForArticle(Integer id) {
        return commentRepository.findCommentsForArticle(id);
    }

//    public List<Comment> allComments (){return commentRepository.allComments();}

}
