package com.papple.blog.security.services;

import java.util.Optional;

import com.papple.blog.models.Comment;

public interface CommentService {
    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
}