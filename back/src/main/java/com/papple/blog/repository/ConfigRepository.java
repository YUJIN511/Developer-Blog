package com.papple.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.papple.blog.models.BlogConfig;

@Repository
public interface ConfigRepository extends JpaRepository<BlogConfig, String>{
	@Transactional
	@Modifying
	@Query(value = "update config set picture = ?1 where email = ?2", nativeQuery = true)
	void updatePicture(String picture, String email);
}
