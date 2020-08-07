package com.papple.blog.controllers;

import java.time.LocalDateTime;

import com.papple.blog.models.Comment;
import com.papple.blog.models.Notification;
import com.papple.blog.models.Post;
import com.papple.blog.payload.request.CommentRequest;
import com.papple.blog.repository.UserRepository;
import com.papple.blog.security.services.CommentService;
import com.papple.blog.security.services.NotificationService;
import com.papple.blog.security.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

// http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {

	@Autowired
    private CommentService commentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/writeComment")
    @ApiOperation(value = "새 댓글 쓰기")
    public ResponseEntity<String> writeComment(@RequestBody Comment comment) {
    
        commentService.save(comment);     // 댓글 저장
    
        // 알림 발생(0000010)
        Post post = postService.findById(comment.getPostid()).get();

        String actionName = userRepository.getUserByEmail(comment.getEmail()).getNickname();

        Notification notification = Notification.builder()
            .message(actionName +"님이 회원님의 게시물에 댓글을 남겼습니다. "+comment.getContent())
            .actionuser(comment.getEmail())
            .targetuser(post.getWriter())
            .notiurl("http://i3a604.p.ssafy.io/post/postDetail/"+comment.getPostid())
            .build();
            
        notification.setPostid(comment.getPostid());
        notification.setType(1<<1);
        notificationService.save(notification);
        
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    
    @PutMapping("/modifyComment")
	@ApiOperation(value = "댓글 수정 ")
	public ResponseEntity<String> modifyComment(@RequestBody CommentRequest commentRequest) {

        Comment comment = commentService.findById(commentRequest.getId()).get();
        comment.setContent(commentRequest.getContent());
        comment.setCreatedate(LocalDateTime.now());
        commentService.save(comment);
        
        return new ResponseEntity<>("success", HttpStatus.OK);
	}	

}

