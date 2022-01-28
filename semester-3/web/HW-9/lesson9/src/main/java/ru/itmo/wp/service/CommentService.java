package ru.itmo.wp.service;

import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.repository.CommentRepository;

/**
 * @author Pavel Lymar
 */
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }
}
