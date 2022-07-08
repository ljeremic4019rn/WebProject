package rs.raf.repositories.IRepos;


import rs.raf.models.Article;

import java.util.List;

public interface ArticleRepository {
    public Article addArticle(Article article);
    public Article editArticle(Article article);
    public Article findArticle(Integer id);
    public Integer countArticles(Integer catId, Integer tagId);
    public void deleteArticle(Integer id);
    public List<Article> allArticles();
    public List<Article> findArticlesPage(Integer page);
    public List<Article> findArticlesByCategory(Integer categoryId);
    public List<Article> findArticlesByTag(Integer tagId);
    public List<Article> findMostRecentArticles();
    public List<Article> findMostReadMonthlyArticles();
    public List<Article> searchArticle(String search);
    public void increaseVisits (Integer articleId);
    public List<Article> articlesByPage(Integer pageNum);
    public List<Article> arByCatByPage(Integer categoryId, Integer pageNum);
}
