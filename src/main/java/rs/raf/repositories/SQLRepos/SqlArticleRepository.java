package rs.raf.repositories.SQLRepos;


import rs.raf.models.Article;
import rs.raf.repositories.IRepos.ArticleRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlArticleRepository extends MySqlAbstractRepository implements ArticleRepository {


    @Override
    public void deleteArticle(Integer id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

        try {
        connection = this.newConnection();
        preparedStatement = connection.prepareStatement("DELETE FROM articles WHERE vest_id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("DELETE FROM comments WHERE vest_id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("DELETE FROM tag_article WHERE article_id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        this.closeConnection(connection);
        this.closeStatement(preparedStatement);
    }
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
