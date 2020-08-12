package com.papple.blog.security.services;

import com.papple.blog.models.Notification;
import com.papple.blog.payload.response.StreamDataSet;


public interface NotificationService {

    // 알람기능
    void addEmitter(String email, StreamDataSet dataSet);
    void removeEmitter(String email);
    void fetch();

    // 부가기능
    Notification save(Notification notification);
    Notification findByActionuserAndPostidAndType(String actionuser, Long postid, Integer type);
    Notification findByActionuserAndCommentidAndType(String actionuser, Long commentid, Integer type);
    Notification findByActionuserAndTargetuserAndType(String follower, String followed, Integer type);
    void deleteByTargetuserAndType(String targetuser, Integer type);
}