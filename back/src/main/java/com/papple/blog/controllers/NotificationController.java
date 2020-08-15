package com.papple.blog.controllers;

import java.util.List;

import com.papple.blog.models.Notification;
import com.papple.blog.models.User;
import com.papple.blog.payload.response.StreamDataSet;
import com.papple.blog.repository.UserRepository;
import com.papple.blog.security.services.NotificationService;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.swagger.annotations.ApiOperation;

// @CrossOrigin(origins = "http://i3a604.p.ssafy.io", allowedHeaders = "*",allowCredentials = "true")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*",allowCredentials = "true")
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
	UserRepository userRepository;
	@Autowired
	NotificationService notificationService;

    @GetMapping(value = "/push", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ApiOperation(value = "알림 push")
    public ResponseEntity<SseEmitter> fetchNotify(@RequestParam(required = false) String email) {

        final User user = userRepository.getUserByEmail(email);
        if(user == null){   // 식별되지 않은 사용자인 경우
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        final SseEmitter emitter = new SseEmitter();
        final StreamDataSet DATA_SET = new StreamDataSet(user, emitter);
        final String UNIQUE_UUID = email;

        try {
            notificationService.addEmitter(UNIQUE_UUID, DATA_SET);
        } catch (Exception e) {
            e.printStackTrace();
        }

        emitter.onCompletion(() -> {
            notificationService.removeEmitter(UNIQUE_UUID);
        });
        emitter.onTimeout(() -> {
            emitter.complete();
            notificationService.removeEmitter(UNIQUE_UUID);
        });

        return new ResponseEntity<>(emitter, HttpStatus.OK);
    }

    @GetMapping(value="/updateIsread")
    @ApiOperation(value = "Isread == true")
    public ResponseEntity<?> updateIsread(@RequestParam Long id) {
        notificationService.updateIsreadById(id);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    
    @GetMapping(value="/all")
    @ApiOperation(value = "모든 알림 조회")
    public ResponseEntity<List<Notification>> allNotification(@RequestParam String email) {
        List<Notification> list = notificationService.findByTargetuser(email);
        for(Notification noti : list)   noti.setProfile(userRepository.getUserByEmail(noti.getActionuser()).getProfile());

        return new ResponseEntity<List<Notification>>(list, HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "알림 삭제")
    public ResponseEntity<?> deleteNotification(@RequestParam Long id){
        notificationService.deleteById(id);
        
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }


}