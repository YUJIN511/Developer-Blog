package com.papple.blog.repository;

import javax.transaction.Transactional;

import com.papple.blog.models.History;
import com.papple.blog.models.PKSet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository  extends JpaRepository<History, PKSet>{
    
    @Transactional
	@Modifying
	@Query(value="DELETE FROM history WHERE postid=?1", nativeQuery = true)
    void deleteByPostid(Long id); 	// 글 삭제시 히스토리 글 삭제
    
    @Transactional
	@Modifying
	@Query(value="DELETE FROM history WHERE email=?1", nativeQuery = true)
    void deleteByEmail(String email); 	// 회원 탈퇴시 히스토리 글 삭제

}