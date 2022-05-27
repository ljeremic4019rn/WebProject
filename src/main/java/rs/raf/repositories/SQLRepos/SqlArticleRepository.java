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
    public Article addArticle(Article article, String[] tags) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO article (title, content, author_id, category_id)" +
                            " VALUES (?, ?, ?, ?)",
                    generatedColumns
            );
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setInt(3, article.getAuthorId());
            preparedStatement.setInt(4, article.getCategoryId());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Integer articleId = resultSet.getInt(1);

//            closeResultSet(resultSet);
//            closeStatement(preparedStatement);

            for (String tag: tags) {
                preparedStatement = connection.prepareStatement(
                        "SELECT * FROM tag WHERE tag_name = ?"
                );
                preparedStatement.setString(1, tag);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO tag (tag_name) VALUES (?)"
                    );
                    preparedStatement.setString(1, tag);
                    preparedStatement.executeUpdate();
                }
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO tag_news_article (tag_id, article_id) VALUES ((SELECT id FROM tag WHERE tag_name = ?), ?)"
                );
                preparedStatement.setString(1, tag);
                preparedStatement.setInt(2, articleId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return article;
    }

    @Override
    public Article editArticle(Article article, String[] tags) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE article SET title = ?, content = ?, category_id = ? WHERE id = ?"
            );
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setInt(3, article.getCategoryId());
            preparedStatement.setInt(4, article.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    "DELETE FROM tag_news_article WHERE article_id = ?"
            );
            preparedStatement.setInt(1, article.getId());
            preparedStatement.executeUpdate();

            for (String tag: tags) {
                preparedStatement = connection.prepareStatement(
                        "SELECT * FROM tag WHERE tag_name = ?"
                );
                preparedStatement.setString(1, tag);
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO tag (tag_name) VALUES (?)"
                    );
                    preparedStatement.setString(1, tag);
                    preparedStatement.executeUpdate();
                }
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO tag_news_article (tag_id, article_id) VALUES ((SELECT id FROM tag WHERE tag_name = ?), ?)"
                );
                preparedStatement.setString(1, tag);
                preparedStatement.setInt(2, article.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return article;
    }

    @Override
    public boolean deleteArticle(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM article WHERE id = ?"
            );
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                preparedStatement = connection.prepareStatement(
                        "DELETE FROM article WHERE id = ?"
                );
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(
                        "DELETE FROM tag_news_article WHERE article_id = ?"
                );
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } else {
                closeStatement(preparedStatement);
                closeResultSet(resultSet);
                closeConnection(connection);
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return true;
    }

    @Override
    public Article getSingleArticle(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Article article = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM article WHERE id = ?"
            );
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                preparedStatement = connection.prepareStatement(
                        "UPDATE article SET num_of_visits = num_of_visits + 1 WHERE id = ?"
                );
                preparedStatement.setInt(1, resultSet.getInt("id"));
                preparedStatement.executeUpdate();

                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
                article.setDate(resultSet.getDate("publish_date"));
                article.setVisits(resultSet.getInt("num_of_visits"));
                article.setAuthorId(resultSet.getInt("author_id"));
                article.setCategoryId(resultSet.getInt("category_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return article;
    }

    @Override
    public List<Article> getArticlesPage(Integer page) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Article> articles = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM article ORDER BY publish_date DESC LIMIT 5 OFFSET ?"
            );
            preparedStatement.setInt(1, (page - 1) * 5);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Article toAdd = new Article();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setCategoryId(resultSet.getInt("category_id"));
                toAdd.setAuthorId(resultSet.getInt("author_id"));
                toAdd.setVisits(resultSet.getInt("num_of_visits"));
                toAdd.setDate(resultSet.getDate("publish_date"));
                toAdd.setContent(resultSet.getString("content"));
                toAdd.setTitle(resultSet.getString("title"));

                articles.add(toAdd);
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
    public List<Article> getArticlesByCategoryPage(Integer categoryId, Integer page) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Article> articles = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM article WHERE category_id = ? ORDER BY publish_date DESC LIMIT 5 OFFSET ?"
            );
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, (page - 1) * 5);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Article toAdd = new Article();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setCategoryId(resultSet.getInt("category_id"));
                toAdd.setAuthorId(resultSet.getInt("author_id"));
                toAdd.setVisits(resultSet.getInt("num_of_visits"));
                toAdd.setDate(resultSet.getDate("publish_date"));
                toAdd.setContent(resultSet.getString("content"));
                toAdd.setTitle(resultSet.getString("title"));

                articles.add(toAdd);
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
    public Integer countArticles(Integer catId, Integer tagId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Integer count = 0;

        try {
            connection = newConnection();

            if (catId == 0 && tagId == 0) {
                preparedStatement = connection.prepareStatement(
                        "SELECT COUNT(*) FROM article"
                );
            } else if (catId != 0 && tagId == 0) {
                preparedStatement = connection.prepareStatement(
                        "SELECT COUNT(*) FROM article WHERE category_id = ?"
                );
                preparedStatement.setInt(1, catId);
            } else {
                preparedStatement = connection.prepareStatement(
                        "SELECT COUNT(*) FROM article INNER JOIN tag_news_article tna " +
                                "ON article.id = tna.article_id WHERE tna.tag_id = ?"
                );
                preparedStatement.setInt(1, tagId);
            }

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }
        return count;
    }

    @Override
    public List<Article> getArticlesByTagPage(Integer tagId, Integer page) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Article> articles = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT article.* FROM article " +
                            "INNER JOIN tag_news_article tna on article.id = tna.article_id " +
                            "WHERE tna.tag_id = ? ORDER BY article.publish_date DESC LIMIT 5 OFFSET ?"
            );
            preparedStatement.setInt(1, tagId);
            preparedStatement.setInt(2, (page - 1) * 5);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Article toAdd = new Article();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setCategoryId(resultSet.getInt("category_id"));
                toAdd.setAuthorId(resultSet.getInt("author_id"));
                toAdd.setVisits(resultSet.getInt("num_of_visits"));
                toAdd.setDate(resultSet.getDate("publish_date"));
                toAdd.setContent(resultSet.getString("content"));
                toAdd.setTitle(resultSet.getString("title"));

                articles.add(toAdd);
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
    public List<Article> getMostRecentArticles() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Article> articles = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM article ORDER BY publish_date DESC LIMIT 10"
            );
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Article toAdd = new Article();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setCategoryId(resultSet.getInt("category_id"));
                toAdd.setAuthorId(resultSet.getInt("author_id"));
                toAdd.setVisits(resultSet.getInt("num_of_visits"));
                toAdd.setDate(resultSet.getDate("publish_date"));
                toAdd.setContent(resultSet.getString("content"));
                toAdd.setTitle(resultSet.getString("title"));

                articles.add(toAdd);
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
    public List<Article> getMostReadMonthlyArticles() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Article> articles = new ArrayList<>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM article WHERE publish_date BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) " +
                            "AND NOW() ORDER BY num_of_visits DESC LIMIT 10"
            );
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Article toAdd = new Article();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setCategoryId(resultSet.getInt("category_id"));
                toAdd.setAuthorId(resultSet.getInt("author_id"));
                toAdd.setVisits(resultSet.getInt("num_of_visits"));
                toAdd.setDate(resultSet.getDate("publish_date"));
                toAdd.setContent(resultSet.getString("content"));
                toAdd.setTitle(resultSet.getString("title"));

                articles.add(toAdd);
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
}
