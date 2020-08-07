package com.papple.blog.security.services;

import java.util.Optional;

import com.papple.blog.models.Comment;
import com.papple.blog.repository.CommentRopository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRopository commentRopository;
    
    @Override
    public Comment save(Comment comment) {
        return commentRopository.save(comment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRopository.findById(id);
    }

}