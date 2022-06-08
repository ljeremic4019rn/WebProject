package rs.raf.repositories.SQLRepos;


import rs.raf.models.Article;
import rs.raf.repositories.IRepos.ArticleRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlArticleRepository extends MySqlAbstractRepository implements ArticleRepository {


    @Override
    public void deleteArticle(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM articles WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM comments WHERE articleId = ?");//todo ako nema komentara da li puca
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM tags_articles WHERE articleId = ?");
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();

//            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO articles (categoryId, title, content, authorId, publishedDate, visits) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP(), 0)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, article.getCategoryId());
            preparedStatement.setString(2, article.getTitle());
            preparedStatement.setString(3, article.getContent());
            preparedStatement.setInt(4, article.getAuthorId());
            //preparedStatement.setString(5, category.getName()); TODO: proveriti date i visits
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                article.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return article;
    }

    @Override
    public Article editArticle(Article article) {
        System.out.println("ARTIKAL");
        System.out.println(article.toString());

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE articles SET categoryId = ?, title = ?, content = ?, authorId = ?, visits = ? WHERE id = ?");
            preparedStatement.setInt(1, article.getCategoryId());
            preparedStatement.setString(2, article.getTitle());
            preparedStatement.setString(3, article.getContent());
            preparedStatement.setInt(4, article.getAuthorId());
            preparedStatement.setInt(5, article.getVisits());
            preparedStatement.setInt(6, article.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return article;
    }

    @Override
    public Article findArticle(Integer id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Article article = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM articles WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                article = new Article(
                        resultSet.getInt("id"),
                        resultSet.getInt("categoryId"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getInt("authorId"),
                        resultSet.getDate("publishedDate"),
                        resultSet.getInt("visits"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return article;
    }
    @Override
    public Integer countArticles(Integer catId, Integer tagId) {
        return null;
    }

    @Override
    public List<Article> allArticles() {

        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM articles");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Article article = new Article(
                        resultSet.getInt("id"),
                        resultSet.getInt("categoryId"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getInt("authorId"),
                        resultSet.getDate("publishedDate"),
                        resultSet.getInt("visits"));
                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
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
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM articles ORDER BY publishedDate DESC");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Article article = new Article(
                        resultSet.getInt("id"),
                        resultSet.getInt("categoryId"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getInt("authorId"),
                        resultSet.getDate("publishedDate"),
                        resultSet.getInt("visits"));
                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return articles;
    }

    @Override
    public List<Article> findMostReadMonthlyArticles() {
        return null;
    }
}
