package ru.itmo.wp.web.page;

import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Pavel Lymar
 */
public class TalksPage extends Page {
    @Override
    void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            throw new RedirectException("/index");
        }
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        String targetUserId = (String) request.getSession().getAttribute("targetUserId");
        view.put("users", userService.findAll());
        if (targetUserId != null) {
            view.put("targetUserId", Long.parseLong(targetUserId));
            view.put("talks", talkService.getMessages(getUser().getId(), Long.parseLong(targetUserId)));
        }
    }

    protected void setTarget(HttpServletRequest request, Map<String, Object> view) {
        request.getSession().setAttribute("targetUserId", request.getParameter("targetUserId"));
        throw new RedirectException("/talks");
    }

    protected void sendMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = request.getParameter("message");
        if (message != null && !message.isEmpty() && message.length() <= 255) {
            talkService.saveMessage(getUser().getId(),
                    Long.parseLong((String) request.getSession().getAttribute("targetUserId")),
                    message);
        }
        throw new RedirectException("/talks");
    }
}
