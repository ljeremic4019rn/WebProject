package rs.raf.repositories.IRepos;


import rs.raf.models.Article;

import java.util.List;

public interface ArticleRepository {
    public boolean deleteArticle(Integer id);
    public Article addArticle(Article article, String[] tags);
    public Article editArticle(Article article, String[] tags);
    public Article getSingleArticle(Integer id);
    public Integer countArticles(Integer catId, Integer tagId);
    public List<Article> getArticlesPage(Integer page);
    public List<Article> getArticlesByCategoryPage(Integer categoryId, Integer page);
    public List<Article> getArticlesByTagPage(Integer tagId, Integer page);
    public List<Article> getMostRecentArticles();
    public List<Article> getMostReadMonthlyArticles();
}
