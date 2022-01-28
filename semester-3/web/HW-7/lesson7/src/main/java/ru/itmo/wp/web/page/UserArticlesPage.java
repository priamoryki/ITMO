package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Pavel Lymar
 */
public class UserArticlesPage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("articles", articleService.findByUserId(user.getId()));
        }
    }

    private void changeArticle(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new ValidationException("Can't parse user id");
        }
        User user = (User) request.getSession().getAttribute("user");
        if (articleService.find(id).getUserId() != user.getId()) {
            throw new ValidationException("You can't change status of this article");
        }
        articleService.setArticleStatus(id, Boolean.parseBoolean(request.getParameter("status")));
    }
}
