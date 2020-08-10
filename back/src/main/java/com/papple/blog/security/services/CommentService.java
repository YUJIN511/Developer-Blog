package com.papple.blog.security.services;

import java.util.List;
import java.util.Optional;

import com.papple.blog.models.Comment;

public interface CommentService {
    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
    List<Comment> findByPostidAndReplytoIsNull(Long postid);
    List<Comment> findByPostidAndReplyto(Long postid, Long replyto);
    void deleteById(Long id);
    void deleteByReplyto(Long id);
}