package com.papple.blog.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.papple.blog.models.Notification;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

	List<Notification> findByTargetuserAndIsreadIsFalse(String email);				// 안읽은 알림
	Notification findByActionuserAndPostidoflike(String actionuser, Long postid);	// 글 좋아요 눌렀던 사용자 알림
	Notification findByActionuserAndFollowed(String actionuser, String followed);	// 팔로우 알림

	@Transactional
	@Modifying
	@Query(value = "UPDATE notification SET isalert = true WHERE id = ?1", nativeQuery = true)
	void updateIsalertById(Long id);

	@Transactional
	@Modifying
	@Query(value = "TRUNCATE notification", nativeQuery = true)
	void deleteNotificationByCron();
  
}