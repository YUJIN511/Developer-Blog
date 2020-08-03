package com.papple.blog.models;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String email;   // 댓글 단 사용자 이메일

    private Long postid;    // 댓글 단 포스트 id

    private int likes;       // 댓글 좋아요 수

    private Long reply_to;

    private boolean has_replies;
}