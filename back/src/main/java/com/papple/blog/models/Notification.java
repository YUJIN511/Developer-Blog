package com.papple.blog.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="notification")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private boolean isalert;            // 알람이 울렸는지

    private boolean isread;             // 알람을 읽었는지

    private String message;             // 알람 메세지

    private String targetuser;          // 알람 갈 사용자

    private String actionuser;          // 알람 발생시킨 사용자

    private String notiurl;             // 알람 클릭시 이동 url

    @CreationTimestamp
    private LocalDateTime createat;
    
    public Notification(){}

    @Builder
    public Notification(String message, String targetuser, String actionuser, String notiurl){
        this.isalert = false;
        this.isread = false;
        this.message = message;
        this.targetuser = targetuser;
        this.actionuser = actionuser;
        this.notiurl = notiurl;
    }

}