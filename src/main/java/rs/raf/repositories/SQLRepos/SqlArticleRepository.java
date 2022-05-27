package rs.raf.repositories.SQLRepos;


import rs.raf.models.Article;
import rs.raf.repositories.IRepos.ArticleRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlArticleRepository extends MySqlAbstractRepository implements ArticleRepository {


    @Override
    public void deleteArticle(Integer id) {

    }

    @Override
    public Article addArticle(Article article) {
        return null;
    }

    @Override
    public Article editArticle(Article article) {
        return null;
    }

    @Override
    public Article findArticle(Integer id) {
        return null;
    }

    @Override
    public Integer countArticles(Integer catId, Integer tagId) {
        return null;
    }

    @Override
    public List<Article> allArticles() {
        return null;
    }

    @Override
    public List<Article> findArticlesPage(Integer page) {
        return null;
    }

    @Override
    public List<Article> findArticlesByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public List<Article> findArticlesByTag(Integer tagId) {
        return null;
    }

    @Override
    public List<Article> findMostRecentArticles() {
        return null;
    }

    @Override
    public List<Article> findMostReadMonthlyArticles() {
        return null;
    }
}
