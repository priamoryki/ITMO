package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Lymar
 */
public class ArticleRepositoryImpl implements ArticleRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void save(Article article) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `Article` (`userId`, `title`, `text`, `creationTime`, `hidden`) VALUES (?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, Long.toString(article.getUserId()));
                statement.setString(2, article.getTitle());
                statement.setString(3, article.getText());
                statement.setBoolean(4, article.isHidden());
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save Article.");
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        article.setId(generatedKeys.getLong(1));
                        article.setCreationTime(find(article.getId()).getCreationTime());
                    } else {
                        throw new RepositoryException("Can't save User [no autogenerated fields].");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save Article.", e);
        }

    }

    @Override
    public Article find(long id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Article WHERE id=?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toArticle(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Article WHERE NOT hidden ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    Article article;
                    while ((article = toArticle(statement.getMetaData(), resultSet)) != null) {
                        articles.add(article);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article.", e);
        }

        return articles;
    }

    @Override
    public List<Article> findUserId(long id) {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Article WHERE userId=?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    Article article;
                    while ((article = toArticle(statement.getMetaData(), resultSet)) != null) {
                        articles.add(article);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article.", e);
        }
        return articles;
    }

    @Override
    public void setArticleStatus(long id, boolean status) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `Article` SET hidden=? WHERE id=?")) {
                statement.setBoolean(1, status);
                statement.setString(2, Long.toString(id));
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't set status Article.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article.", e);
        }
    }

    private Article toArticle(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                default:
                    // No operations.
            }
        }

        return article;
    }
}
