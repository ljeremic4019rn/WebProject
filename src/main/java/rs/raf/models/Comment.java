package rs.raf.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Comment {

    private Integer id;
    private Integer postId;
    @NotNull(message = "author can't be null")
    @NotEmpty(message = "author cant be empty")
    private String authorName;
    @NotNull(message = "content can't be null")
    @NotEmpty(message = "content cant be empty")
    private String content;
    @NotNull(message = " can't be null")
    @NotEmpty(message = " cant be empty")
    private Date date;

    public Comment() {
    }

    public Comment(Integer id, Integer postId, String authorName, String content, Date date) {
        this.id = id;
        this.postId = postId;
        this.authorName = authorName;
        this.content = content;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
