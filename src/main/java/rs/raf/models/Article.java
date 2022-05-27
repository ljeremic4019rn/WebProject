package rs.raf.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Article {

    private Integer id;
    private Integer categoryId;
    @NotNull(message = "title can't be null")
    @NotEmpty(message = "title cant be empty")
    private String title;
    @NotNull(message = "content can't be null")
    @NotEmpty(message = "content cant be empty")
    private String content;
    private Integer authorId;
    @NotNull(message = "date can't be null")
    @NotEmpty(message = "date cant be empty")
    private Date date;
    private Integer visits;
    private List<Tag> tags = new ArrayList<>();

    public Article() {
    }

    public Article(Integer id, Integer categoryId, String title, String content, Integer authorId, Date date, Integer visits) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.date = date;
        this.visits = visits;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }
}
