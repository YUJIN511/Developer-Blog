package com.papple.blog.security.services;

import com.papple.blog.models.Comment;

public interface CommentService {
    Comment save(Comment comment);
}