package com.papple.blog.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import com.papple.blog.models.Comment;
import com.papple.blog.models.Notification;
import com.papple.blog.models.Post;
import com.papple.blog.payload.request.CommentRequest;
import com.papple.blog.repository.CommentReopository;
import com.papple.blog.repository.UserRepository;
import com.papple.blog.security.services.CommentService;
import com.papple.blog.security.services.NotificationService;
import com.papple.blog.security.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private CommentReopository commentReopository;
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
    
        commentService.save(comment);     // 댓글또는답댓글 저장

        Post post = postService.findById(comment.getPostid()).get();
        String actionName = userRepository.getUserByEmail(comment.getEmail()).getNickname();
       
        if(comment.getReplyto()==null){ // 댓글
        
            if(!comment.getEmail().equals(post.getWriter())){   // 자신의 게시물일 경우 X
                // 알림 발생(0000010)
                Notification notification = Notification.builder()
                    .message(actionName +"님이 회원님의 게시물에 댓글을 남겼습니다. "+comment.getContent())
                    .actionuser(comment.getEmail())
                    .targetuser(post.getWriter())
                    .notiurl("http://i3a604.p.ssafy.io/post/postDetail/"+comment.getPostid())
                    .build();
                    
                notification.setPostid(comment.getPostid());
                notification.setType(1<<1);
                notificationService.save(notification);
            }

        } else{ // 답댓글
            Comment comm = commentReopository.findById(comment.getReplyto()).get();
            comm.setHasreplies(true);
            commentService.save(comment);   // 부모댓글 수정

            if(!comment.getEmail().equals(comm.getEmail())){ // 자신의 댓글에 답글 다는 경우는 X
                // 알림 발생(0001000)
                Notification notification = Notification.builder()
                    .message(actionName +"님이 답글에서 회원님을 언급했습니다. "+comment.getContent())
                    .actionuser(comment.getEmail())
                    .targetuser(comm.getEmail())
                    .notiurl("http://i3a604.p.ssafy.io/post/postDetail/"+comment.getPostid())
                    .build();
                    
                notification.setPostid(comment.getPostid());
                notification.setType(1<<3);
                notificationService.save(notification);
            }
        }
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

    @DeleteMapping
	@ApiOperation(value = "댓글 삭제 - 답댓글 삭제")
	public ResponseEntity<String> deleteComment(Long id) {

        Optional<Comment> comment = commentService.findById(id);

        if(comment!=null){  // 댓글 삭제
            commentService.deleteById(id);      
        }

        if(comment.get().isHasreplies()){ // 답댓글 삭제
            commentService.deleteByReplyto(id);
        }

		return new ResponseEntity<String>("success", HttpStatus.OK);
	
	}
}

