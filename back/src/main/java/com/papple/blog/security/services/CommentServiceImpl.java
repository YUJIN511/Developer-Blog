package com.papple.blog.security.services;

import java.util.List;
import java.util.Optional;

import com.papple.blog.models.Comment;
import com.papple.blog.repository.CommentReopository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentReopository commentRopository;

    @Override
    public Comment save(Comment comment) {
        return commentRopository.save(comment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRopository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        commentRopository.deleteById(id);
    }

    @Override
    public void deleteByReplyto(Long id) {
        commentRopository.deleteByReplyto(id);
    }

    @Override
    public List<Comment> findByPostidAndReplytoIsNull(Long postid) {
        return commentRopository.findByPostidAndReplytoIsNull(postid);
    }

    @Override
    public List<Comment> findByPostidAndReplyto(Long postid, Long replyto) {
        return commentRopository.findByPostidAndReplyto(postid, replyto);
    }

    @Override
    public void deleteByPostid(Long postid) {
        commentRopository.deleteByPostid(postid);
    }

}