package com.papple.blog.controllers;

import com.papple.blog.models.Comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

// http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {
//	@Autowired
    

    @PostMapping
    @ApiOperation(value = "새 댓글 쓰기")
    public ResponseEntity<String> insert(@RequestBody Comment commemnt) {

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
        

}

