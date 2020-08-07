package com.papple.blog.repository;

import com.papple.blog.models.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRopository extends JpaRepository<Comment, Long> {

    
}