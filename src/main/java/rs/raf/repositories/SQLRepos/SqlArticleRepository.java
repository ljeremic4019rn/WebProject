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

            preparedStatement = connection.prepareStatement("INSERT INTO articles (" +
                    "categoryId, title, content, authorId, publishedDate, visits) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP(), 0)",
                    Statement.RETURN_GENERATED_KEYS);
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
    public List<Article> findArticlesPage(Integer page) {
        return null;
    }

    @Override
    public List<Article> findArticlesByCategory(Integer categoryId) {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM articles  WHERE categoryId = ? ORDER BY publishedDate ASC");
            preparedStatement.setInt(1, categoryId);
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
    public List<Article> findArticlesByTag(Integer tagId) {
        System.out.println("USLI ");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Article> articles = new ArrayList<>();

        try {
            connection = newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM articles ar INNER JOIN tags_articles ta on ar.id = ta.articleId " +
                            "WHERE ta.tagId = ? ORDER BY ar.publishedDate DESC");// LIMIT 5 OFFSET ?
            preparedStatement.setInt(1, tagId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Article toAdd = new Article();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setCategoryId(resultSet.getInt("categoryId"));
                toAdd.setAuthorId(resultSet.getInt("authorId"));
                toAdd.setVisits(resultSet.getInt("visits"));
                toAdd.setDate(resultSet.getDate("publishedDate"));
                toAdd.setContent(resultSet.getString("content"));
                toAdd.setTitle(resultSet.getString("title"));

                articles.add(toAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }
        return articles;
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Article> articles = new ArrayList<>();

        try {
            connection = newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM articles WHERE publishedDate BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW()  ORDER BY visits DESC LIMIT 10"
            );
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Article toAdd = new Article();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setCategoryId(resultSet.getInt("categoryId"));
                toAdd.setAuthorId(resultSet.getInt("authorId"));
                toAdd.setVisits(resultSet.getInt("visits"));
                toAdd.setDate(resultSet.getDate("publishedDate"));
                toAdd.setContent(resultSet.getString("content"));
                toAdd.setTitle(resultSet.getString("title"));

                articles.add(toAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return articles;
    }

    @Override
    public List<Article> searchArticle(String search) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Article> articles = new ArrayList<>();

        String searchQuote = "%" + search + "%";


        try {
            connection = newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM articles WHERE title LIKE  ? OR content LIKE ? ");
            preparedStatement.setString(1, searchQuote);
            preparedStatement.setString(2, searchQuote);


            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Article toAdd = new Article();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setCategoryId(resultSet.getInt("categoryId"));
                toAdd.setAuthorId(resultSet.getInt("authorId"));
                toAdd.setVisits(resultSet.getInt("visits"));
                toAdd.setDate(resultSet.getDate("publishedDate"));
                toAdd.setContent(resultSet.getString("content"));
                toAdd.setTitle(resultSet.getString("title"));

                articles.add(toAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }
        return articles;
    }

    @Override
    public void increaseVisits(Integer articleId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE articles SET visits = visits + 1 WHERE id = ?");
            preparedStatement.setInt(1, articleId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public List<Article> articlesByPage(Integer pageNum) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Article> articles = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM articles ORDER BY publishedDate DESC LIMIT 5 OFFSET ?");
            preparedStatement.setInt(1, (pageNum - 1) * 5);

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return articles;
    }

    @Override
    public List<Article> arByCatByPage(Integer categoryId, Integer pageNum) {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM articles  WHERE categoryId = ? ORDER BY publishedDate ASC LIMIT 5 OFFSET ?");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, (pageNum - 1) * 5);
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

}
