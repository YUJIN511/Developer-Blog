package com.papple.blog.repository;

import java.util.List;

import com.papple.blog.payload.response.CommentResponse;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CommentListRepository {
    List<CommentResponse> findByPostidAndReplytoIsNull(Long postid);
    List<CommentResponse> findByPostidAndReplyto(Long postid, Long replyto);
}