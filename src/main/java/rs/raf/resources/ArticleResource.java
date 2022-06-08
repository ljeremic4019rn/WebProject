package rs.raf.resources;

import rs.raf.models.Article;
import rs.raf.services.ArticleService;
import rs.raf.services.CommentService;
import rs.raf.services.TagService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/articles")
public class ArticleResource {

    @Inject
    private ArticleService articleService;
    @Inject
    private CommentService commentService;
    @Inject
    private TagService tagService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allArticles(){
        return Response.ok(this.articleService.allArticles()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Article findArticle(@PathParam("id") Integer id) {
        return this.articleService.findArticle(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Article createArticle(@Valid Article article) {
        return this.articleService.addArticle(article);
    }

//    @DELETE todo mozda ovde ostaviti jer user brise svoj artikal
//    @Path("/{id}")
//    public void deleteArticle(@PathParam("id") Integer id) {
//        this.articleService.deleteArticle(id);
//    }

//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    public Article updateArticle(@Valid Article article) {
//        return this.articleService.editArticle(article);
//    }

    @GET
    @Path("/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response articleComments (@PathParam("id") Integer id) {
        return Response.ok(this.commentService.findCommentsForArticle(id)).build();
    }

    @GET
    @Path("/category/{id}")//(category_id)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findArticlesByCategory(@PathParam("id") Integer id) {
        return Response.ok(this.articleService.findArticlesByCategory(id)).build();
    }

    @GET
    @Path("/tag/{id}")//(tag_id)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findArticlesByTag (@PathParam("id") Integer id) {
        return Response.ok(this.articleService.findArticlesByTag(id)).build();
    }

    @GET
    @Path("/{id}/tags")//(article_id)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findArticleTags(@PathParam("id") Integer id) {
        return Response.ok(this.tagService.tagsFromArticle(id)).build();
    }

    @GET
    @Path("/most_recent")//(article_id)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findMostRecentArticles(){
        return Response.ok(this.articleService.findMostRecentArticles()).build();
    }

    @GET
    @Path("/most_read")//(article_id)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findMostReadMonthlyArticles(){
        return Response.ok(this.articleService.findMostReadArticlesMonthly()).build();
    }
}
