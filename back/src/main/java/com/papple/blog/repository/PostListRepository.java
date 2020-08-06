package com.papple.blog.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import com.papple.blog.models.Hashtag;
import com.papple.blog.models.Post;
import com.papple.blog.payload.response.PostList;

@Mapper
public interface PostListRepository{
	List<PostList> searchAllPost();
	List<PostList> searchByEmail(String email);
}
