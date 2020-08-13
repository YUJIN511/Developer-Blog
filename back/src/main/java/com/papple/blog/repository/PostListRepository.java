package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.PostDetail;
import com.papple.blog.payload.response.PostList;

@Mapper
public interface PostListRepository{
	List<PostList> searchAllPost();
	List<PostList> searchByEmail(String email);
	PostList searchPostById(Long postid);
	
	PostDetail searchPostDetail(Long postid);
	List<String> searchHashtag(Long postid);
	List<Long> findHistoryByUser(String email);	// 해당 사용자의 방문 기록 검색
}
