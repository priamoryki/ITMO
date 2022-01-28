package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.lesson8.domain.User;
import ru.itmo.wp.lesson8.service.UserService;

/**
 * @author Pavel Lymar
 */
@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String userInfo(@PathVariable String id, Model model) {
        long userId;
        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return "UserPage";
        }

        model.addAttribute("currentUser", userService.findById(userId));
        return "UserPage";
    }
}
