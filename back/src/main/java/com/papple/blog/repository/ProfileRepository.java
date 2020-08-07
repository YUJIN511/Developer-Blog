package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileRepository {
	List<String> searchByEmail(String email);
	int insertProfile(String email, String path);
	int deleteProfile(String email, String path);
	int unProfile(String email);
}
