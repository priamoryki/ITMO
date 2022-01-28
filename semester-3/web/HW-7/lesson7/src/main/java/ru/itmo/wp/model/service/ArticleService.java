package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;

/**
 * @author Pavel Lymar
 */
public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public void save(Article article) {
        articleRepository.save(article);
    }

    public void validateArticle(Article article) throws ValidationException {
        if (article.getTitle() == null || article.getTitle().isEmpty()) {
            throw new ValidationException("The title cannot be empty");
        }
        if (article.getText() == null || article.getText().isEmpty()) {
            throw new ValidationException("Text cannot be empty");
        }
        if (userRepository.find(article.getUserId()) == null) {
            throw new ValidationException("No such user");
        }
        if (article.getText().isEmpty() || article.getText().length() > 255) {
            throw new ValidationException("No such user");
        }
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findByUserId(long id) {
        return articleRepository.findUserId(id);
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public void setArticleStatus(long id, boolean status) {
        articleRepository.setArticleStatus(id, status);
    }
}
