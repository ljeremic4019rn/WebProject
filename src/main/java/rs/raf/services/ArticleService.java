package rs.raf.services;


import rs.raf.models.Article;
import rs.raf.repositories.IRepos.ArticleRepository;

import javax.inject.Inject;
import java.util.List;

public class ArticleService {

    @Inject
    private ArticleRepository articleRepository;

    public Article addArticle(Article article) {
        return articleRepository.addArticle(article);
    }

    public List<Article> allArticles(){
        return articleRepository.allArticles();
    }

    public Article findArticle(Integer id) {
        return articleRepository.findArticle(id);
    }

    public void deleteArticle(Integer id) {
         articleRepository.deleteArticle(id);
    }

    public Article editArticle(Article article) {
        return articleRepository.editArticle(article);
    }

    public List<Article> findArticlesPage(Integer page) {
        return articleRepository.findArticlesPage(page);
    } //todo pogledaj kasnije

    public Integer findCount(Integer catId, Integer tagId) {
        return articleRepository.countArticles(catId, tagId);
    }

    public List<Article> findArticlesByCategory(Integer catId) {
        return articleRepository.findArticlesByCategory(catId);
    }

    public List<Article> findArticlesByTag(Integer tagId) {
        return articleRepository.findArticlesByTag(tagId);
    }

    public List<Article> findMostRecentArticles() {
        return articleRepository.findMostRecentArticles();
    }

    public List<Article> findMostReadArticlesMonthly() {
        return articleRepository.findMostReadMonthlyArticles();
    }
}
