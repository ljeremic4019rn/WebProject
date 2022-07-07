package rs.raf.resources.cms;

import rs.raf.models.Article;
import rs.raf.services.ArticleService;
import rs.raf.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/cms_articles")
public class CmsArticleResource {

    @Inject
    private ArticleService articleService;
    @Inject
    private CommentService commentService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Article addArticle(Article article) {
        return this.articleService.addArticle(article);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Article updateArticle(@Valid Article article) {
        return this.articleService.editArticle(article);
    }

    @DELETE
    @Path("/{id}")
    public void deleteArticle(@PathParam("id") Integer id) {
        this.articleService.deleteArticle(id);
    }

    @GET
    @Path("/search/{search}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> searchArticle(@PathParam("search") String search){
        return this.articleService.searchArticle(search);
    }

}
