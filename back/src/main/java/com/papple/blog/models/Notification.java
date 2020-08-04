package com.papple.blog.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name="notification")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private boolean alert = false;

    private boolean isread = false;

    private String message;

    private String targetuser;

    private String actionuser;

    private String notiurl;

    @CreationTimestamp
    private LocalDateTime createat;
    
    public Notification( boolean alert, boolean isread, String message, String targetuser, String actionuser, String notiurl){
        this.alert = alert;
        this.isread = isread;
        this.message = message;
        this.targetuser = targetuser;
        this.actionuser = actionuser;
        this.notiurl = notiurl;
    }

}