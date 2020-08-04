package com.papple.blog.security.services;

import com.papple.blog.models.Notification;
import com.papple.blog.payload.response.StreamDataSet;


public interface NotificationService {

    void addEmitter(String email, StreamDataSet dataSet);
    void removeEmitter(String email);
    void fetch();
    Notification save(Notification notification);
}