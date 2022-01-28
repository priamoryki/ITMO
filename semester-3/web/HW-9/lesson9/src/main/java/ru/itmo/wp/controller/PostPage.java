package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Pavel Lymar
 */
@Controller
public class PostPage extends Page {
    private final long NEUTRAL_ID = -1;
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String post(@PathVariable String id, Model model) {
        long postId = parsePostId(id);

        Post currentPost = postId == NEUTRAL_ID ? null : postService.findById(postId);
        model.addAttribute("currentPost", currentPost);
        model.addAttribute("commentForm", new CommentForm());
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String post(@PathVariable String id,
                       @Valid @ModelAttribute("commentForm") CommentForm commentForm, BindingResult bindingResult, Model model,
                       HttpSession httpSession) {
        long postId = parsePostId(id);

        Post currentPost = postId == NEUTRAL_ID ? null : postService.findById(postId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("currentPost", currentPost);
            return "PostPage";
        }

        Comment comment = new Comment();
        comment.setUser(getUser(httpSession));
        comment.setText(commentForm.getText());
        if (currentPost != null) {
            postService.addComment(currentPost, comment);
        }

        return "redirect:/post/" + id;
    }

    private long parsePostId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException ignored) {
            return NEUTRAL_ID;
        }
    }
}
