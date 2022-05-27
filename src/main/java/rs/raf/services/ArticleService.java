package rs.raf.services;


import rs.raf.models.Article;
import rs.raf.repositories.IRepos.ArticleRepository;
import rs.raf.repositories.SQLRepos.SqlArticleRepository;

import javax.inject.Inject;
import java.util.List;

public class ArticleService {

    @Inject
    private ArticleRepository articleRepository;

    public Article addArticle(Article article, String[] tags) {
        return articleRepository.addArticle(article, tags);
    }

    public Article getSingleArticle(Integer id) {
        return articleRepository.getSingleArticle(id);
    }

    public boolean deleteArticle(Integer id) {
        return articleRepository.deleteArticle(id);
    }

    public Article editArticle(Article article, String[] tags) {
        return articleRepository.editArticle(article, tags);
    }

    public List<Article> getArticlesPage(Integer page) {
        return articleRepository.getArticlesPage(page);
    }

    public Integer getCount(Integer catId, Integer tagId) {
        return articleRepository.countArticles(catId, tagId);
    }

    public List<Article> getArticlesByCategoryPage(Integer catId, Integer page) {
        return articleRepository.getArticlesByCategoryPage(catId, page);
    }

    public List<Article> getArticlesByTagPage(Integer tagId, Integer page) {
        return articleRepository.getArticlesByTagPage(tagId, page);
    }

    public List<Article> getMostRecentArticles() {
        return articleRepository.getMostRecentArticles();
    }

    public List<Article> getMostReadArticlesMonthly() {
        return articleRepository.getMostReadMonthlyArticles();
    }
}
