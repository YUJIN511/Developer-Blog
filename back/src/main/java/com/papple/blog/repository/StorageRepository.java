package com.papple.blog.repository;

import javax.transaction.Transactional;

import com.papple.blog.models.FollowPK;
import com.papple.blog.models.Storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository  extends JpaRepository<Storage, FollowPK>{
    
	@Transactional
	@Modifying
	@Query(value="DELETE FROM storage WHERE postid=?1", nativeQuery = true)
	void deleteByPostid(Long id); 	// 글 삭제시 보관함 글 삭제

	@Transactional
	@Modifying
	@Query(value="DELETE FROM storage WHERE email=?1", nativeQuery = true)
    void deleteByEmail(String email); 	// 회원 탈퇴시 보관함 글 삭제
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM storage WHERE email=?1 and postid = ?2", nativeQuery = true)
    void deleteByEmailAndPostid(String email, Long postid);
	
	@Query(value = "select count(*) from storage where email = ?1 and postid = ?2", nativeQuery = true)
	int isGood(String email, Long postid);
}