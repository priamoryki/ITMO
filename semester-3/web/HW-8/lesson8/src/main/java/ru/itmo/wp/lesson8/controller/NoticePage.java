package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.lesson8.form.NoticeForm;
import ru.itmo.wp.lesson8.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Pavel Lymar
 */
@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/addNotice")
    public String addNoticePost(@Valid @ModelAttribute("noticeForm") NoticeForm noticeForm,
                                BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }
        if (getUser(httpSession) == null) {
            return "EnterPage";
        }
        noticeService.addNotice(noticeForm);
        return "redirect:/";
    }

    @GetMapping("/addNotice")
    public String addNoticeGet(Model model, HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            return "redirect:/enter";
        }

        model.addAttribute("noticeForm", new NoticeForm());
        return "NoticePage";
    }
}
