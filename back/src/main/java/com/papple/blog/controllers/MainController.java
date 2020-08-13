package com.papple.blog.controllers;

import java.util.List;

import com.papple.blog.models.Post;
import com.papple.blog.models.User;
import com.papple.blog.payload.response.PostList;
import com.papple.blog.repository.PostListRepository;
import com.papple.blog.repository.UserRepository;
import com.papple.blog.security.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/main")
public class MainController {

    @Autowired
	private PostService postService;
	@Autowired
	private PostListRepository postListRepository;
	@Autowired
	private UserRepository userRepository;

    @GetMapping("/historyList")
	@ApiOperation(value = "History 글 리스트")
	public ResponseEntity<List<PostList>> historyList(@RequestParam(required = true) final String email) throws Exception {
		List<PostList> list = postListRepository.findHistoryByUser(email);
		for(PostList post : list){
			User user = userRepository.getUserByEmail(email);
			post.setNickname(user.getNickname());
			post.setProfile(user.getProfile());
			post.setScore(user.getScore());
			// 좋아요 유무 추가
		}
		return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
	}

	@GetMapping("/goodList")
	@ApiOperation(value = "보관함 글 리스트")
	public ResponseEntity<List<Post>> goodList(@RequestParam(required = true) final String email) throws Exception {

		return new ResponseEntity<List<Post>>(postService.findStorageByUser(email), HttpStatus.OK);
	}

	@GetMapping("/followLatest")
	@ApiOperation(value = "팔로우한 사용자들의 최신 글 리스트")
	public ResponseEntity<List<Post>> followLatest(@RequestParam(required = true) final String email) throws Exception {

		return new ResponseEntity<List<Post>>(postService.findFollowLatestByUser(email), HttpStatus.OK);
	}

	@GetMapping("/popular")
	@ApiOperation(value = "인기 포스트 출력(현재 점수합산 : 뷰 + 좋아요 * 2)")
	public ResponseEntity<List<Post>> searchPopularPost() throws Exception {
		System.out.println("인기 포스트 출력");
		return new ResponseEntity<List<Post>>(postService.searchPopularPost(), HttpStatus.OK);
	}

	@GetMapping("/followPopular")
	@ApiOperation(value = "팔로우한 사용자들의 인기 글 리스트")
	public ResponseEntity<List<Post>> followPopular(@RequestParam(required = true) final String email) throws Exception {

		return new ResponseEntity<List<Post>>(postService.findFollowPopularByUser(email), HttpStatus.OK);
	}
}