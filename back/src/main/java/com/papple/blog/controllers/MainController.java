package com.papple.blog.controllers;

import java.util.ArrayList;
import java.util.List;

import com.papple.blog.models.Post;
import com.papple.blog.models.User;
import com.papple.blog.payload.response.PostList;
import com.papple.blog.repository.GoodRepository;
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
	@Autowired
	private GoodRepository goodRepository;

    @GetMapping("/historyList")
	@ApiOperation(value = "History 글 리스트")
	public ResponseEntity<List<PostList>> historyList(@RequestParam(required = true) final String email) throws Exception {
		List<Long> idList = postListRepository.findHistoryByUser(email);
		List<PostList> list = new ArrayList<>();
		for(Long postid : idList){
			PostList post = postListRepository.searchPostById(postid);
			User user = userRepository.getUserByEmail(post.getWriter());	//작성자의 user 정보
			post.setNickname(user.getNickname());
			post.setProfile(user.getProfile());
			post.setScore(user.getScore());
			if(goodRepository.isGood(email, post.getId()) > 0) post.setIsgood(true);
			list.add(post);
		}
		return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
	}

	@GetMapping("/goodList")
	@ApiOperation(value = "좋아요 글 리스트")
	public ResponseEntity<List<PostList>> goodList(@RequestParam(required = true) final String email) throws Exception {
		List<Long> idList = postListRepository.findGoodListByEmail(email);
		List<PostList> list = new ArrayList<>();
		for(Long postid : idList){
			PostList post = postListRepository.searchPostById(postid);
			User user = userRepository.getUserByEmail(post.getWriter());	//작성자의 user 정보
			post.setNickname(user.getNickname());
			post.setProfile(user.getProfile());
			post.setScore(user.getScore());
			if(goodRepository.isGood(email, post.getId()) > 0) post.setIsgood(true);
			list.add(post);
		}
		
//		List<PostList> list = postListRepository.findGoodListByEmail(email);
//		for(PostList post : list) {
//			User user = userRepository.getUserByEmail(post.getWriter());	//작성자의 user 정보
//			post.setNickname(post.getNickname());
//			post.setProfile(user.getProfile());
//			post.setScore(user.getScore());
//			if(goodRepository.isGood(email, post.getId()) > 0) post.setIsgood(true);
//		}
		return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/storageList")
	@ApiOperation(value = "보관함 글 리스트")
	public ResponseEntity<List<PostList>> storageList(@RequestParam(required = true) final String email) throws Exception {
		List<PostList> list = postListRepository.findStorageListByEmail(email);
		for(PostList post : list) {
			User user = userRepository.getUserByEmail(post.getWriter());	//작성자의 user 정보
			post.setNickname(post.getNickname());
			post.setProfile(user.getProfile());
			post.setScore(user.getScore());
			if(goodRepository.isGood(email, post.getId()) > 0) post.setIsgood(true);
		}
		return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
	}

	@GetMapping("/followLatest")
	@ApiOperation(value = "팔로우한 사용자들의 최신 글 리스트")
	public ResponseEntity<List<Post>> followLatest(@RequestParam(required = true) final String email) throws Exception {
		return new ResponseEntity<List<Post>>(postService.findFollowLatestByUser(email), HttpStatus.OK);
	}


	@GetMapping("/followPopular")
	@ApiOperation(value = "팔로우한 사용자들의 인기 글 리스트")
	public ResponseEntity<List<Post>> followPopular(@RequestParam(required = true) final String email) throws Exception {
		return new ResponseEntity<List<Post>>(postService.findFollowPopularByUser(email), HttpStatus.OK);
	}
}