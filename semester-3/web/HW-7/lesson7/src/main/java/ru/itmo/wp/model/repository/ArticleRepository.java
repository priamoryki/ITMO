package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

/**
 * @author Pavel Lymar
 */
public interface ArticleRepository {
    void save(Article article);
    Article find(long id);
    List<Article> findAll();
    List<Article> findUserId(long id);
    void setArticleStatus(long id, boolean status);
}
