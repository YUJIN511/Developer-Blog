package com.papple.blog.controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;

import com.papple.blog.models.GoodList;
import com.papple.blog.models.GoodListPK;
import com.papple.blog.models.Hashtag;
import com.papple.blog.models.HashtagList;
import com.papple.blog.models.HashtagPK;
import com.papple.blog.models.History;
import com.papple.blog.models.HistoryPK;
import com.papple.blog.models.Notification;
import com.papple.blog.models.Post;
import com.papple.blog.models.Storage;
import com.papple.blog.models.StoragePK;
import com.papple.blog.repository.UserRepository;
import com.papple.blog.security.services.HashtagService;
import com.papple.blog.security.services.NotificationService;
import com.papple.blog.security.services.PostService;

// http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class CommentController {
//	@Autowired
	
	

}
