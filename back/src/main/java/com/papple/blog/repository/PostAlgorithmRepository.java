package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.PopularScore;

@Mapper
public interface PostAlgorithmRepository {
	List<PopularScore> getPopularScore();
	List<PopularScore> getCommentScore();

}
