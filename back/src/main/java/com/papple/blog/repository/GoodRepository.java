package com.papple.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.papple.blog.models.GoodList;
import com.papple.blog.models.GoodListPK;

@Repository
public interface GoodRepository extends JpaRepository<GoodList, GoodListPK>{
	@Transactional
	@Modifying
	@Query(value = "insert into goodlist(email, postid) values(?1, ?2)", nativeQuery = true)
	void insertGood(String email, Long postid);
	
	@Transactional
	@Modifying
	@Query(value = "delete from goodlist where email = ?1 and postid = ?2", nativeQuery = true)
	void deleteGood(String email, Long postid);
	
}
