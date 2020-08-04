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

	List<Notification> findByTargetuserAndIsreadIsFalse(String email);

	@Transactional
	@Modifying
	@Query(value = "UPDATE notification SET alert = true WHERE id = ?1", nativeQuery = true)
	void updateAlertById(Long id);

	@Transactional
	@Modifying
	@Query(value = "DELETE TABLE notification", nativeQuery = true)
	void deleteNotificationByCron();
  
}