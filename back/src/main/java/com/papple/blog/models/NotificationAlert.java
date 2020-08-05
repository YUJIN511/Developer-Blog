package com.papple.blog.models;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class NotificationAlert {
    private String email;
	private int notificationCount;
    private List<Notification> notifications;

    @Builder
    public NotificationAlert(String email, int notificationCount, List<Notification> notifications){
        this.email = email;
        this.notificationCount = notificationCount;
        this.notifications = notifications;
    }
}