package rs.raf.resources;

import rs.raf.models.Article;
import rs.raf.models.Comment;
import rs.raf.services.ArticleService;
import rs.raf.services.CommentService;
import rs.raf.services.TagService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/articles")
public class ArticleResource {

    @Inject
    private ArticleService articleService;
    @Inject
    private CommentService commentService;
    @Inject
    private TagService tagService;

//    @Context
//    private ResourceContext resourceContext;



    /*
    all X
    find one X
    create X
    edit X
    delete X
    comments X
    -count ?? nepotrebno
    -find page ?? nepotrebno
    by category X
    by tag X
    -article tags ?? mozda nepotrebno
    recent X
    monthly X
    -tags from post - proveri
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(){
        return Response.ok(this.articleService.allArticles()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Article find(@PathParam("id") Integer id) {
        return this.articleService.findArticle(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Article create(@Valid Article article) {
        return this.articleService.addArticle(article);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        this.articleService.deleteArticle(id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Article update(@Valid Article article) {
        return this.articleService.editArticle(article);
    }

    @GET
    @Path("/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
//    public List<Comment> articleComments (@PathParam("id") Integer id){
//        return this.commentService.findCommentsForArticle(id);
//    }
    public Response articleComments (@PathParam("id") Integer id) {
        return Response.ok(this.commentService.findCommentsForArticle(id)).build();
    }

    @GET
    @Path("/{id}")//(category_id)
    @Produces(MediaType.APPLICATION_JSON)
//    public List<Article> findArticlesByCategory(@PathParam("id") Integer id) {
//        return this.articleService.findArticlesByCategory(id);
//    }
    public Response findArticlesByCategory(@PathParam("id") Integer id) {
        return Response.ok(this.articleService.findArticlesByCategory(id)).build();
    }

    @GET
    @Path("/{id}")//(tag_id)
    @Produces(MediaType.APPLICATION_JSON)
//    public List<Article> findArticlesByTag(@PathParam("id") Integer id) {
//        return this.articleService.findArticlesByTag(id);
//    }
    public Response findArticlesByTag (@PathParam("id") Integer id) {
        return Response.ok(this.articleService.findArticlesByTag(id)).build();
    }

    @GET
    @Path("/{id}")//(article_id)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTagsForArticle (@PathParam("id") Integer id) {
        return Response.ok(this.tagService.tagsForPost(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findMostRecentArticles(){
        return Response.ok(this.articleService.findMostRecentArticles()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findMostReadMonthlyArticles(){
        return Response.ok(this.articleService.findMostReadArticlesMonthly()).build();
    }



}
