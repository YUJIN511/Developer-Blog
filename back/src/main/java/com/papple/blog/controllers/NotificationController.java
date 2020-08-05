package com.papple.blog.controllers;

import com.papple.blog.models.User;
import com.papple.blog.payload.response.StreamDataSet;
import com.papple.blog.repository.UserRepository;
import com.papple.blog.security.services.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
	UserRepository userRepository;
	@Autowired
	NotificationService notificationService;

    @GetMapping(value = "user/push", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
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


}