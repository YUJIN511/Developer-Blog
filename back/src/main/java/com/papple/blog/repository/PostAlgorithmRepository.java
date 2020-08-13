package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.PopularScore;

@Mapper
public interface PostAlgorithmRepository {
	List<PopularScore> getPopularScore();
	List<PopularScore> getCommentScore();
	int insertRead(String email, Long postid);
	int isRead(String email, Long postid);
	List<String> getLookUp(String email);
}
