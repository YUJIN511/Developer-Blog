package com.papple.blog.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import com.papple.blog.models.Post;
import com.papple.blog.models.TagScore;
import com.papple.blog.models.User;
import com.papple.blog.payload.response.PopularScore;
import com.papple.blog.payload.response.PostList;
import com.papple.blog.repository.GoodRepository;
import com.papple.blog.repository.PostAlgorithmRepository;
import com.papple.blog.repository.PostListRepository;
import com.papple.blog.repository.TagScoreRepository;
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
	@Autowired
	private PostAlgorithmRepository algoRepository;
	@Autowired
	private TagScoreRepository tagScoreRepository;

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
		return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/storageList")
	@ApiOperation(value = "보관함 글 리스트")
	public ResponseEntity<List<PostList>> storageList(@RequestParam(required = true) final String email) throws Exception {
//		List<PostList> list = postListRepository.findStorageListByEmail(email);
//		for(PostList post : list) {
//			User user = userRepository.getUserByEmail(post.getWriter());	//작성자의 user 정보
//			post.setNickname(post.getNickname());
//			post.setProfile(user.getProfile());
//			post.setScore(user.getScore());
//			if(goodRepository.isGood(email, post.getId()) > 0) post.setIsgood(true);
//		}
		List<Long> idList = postListRepository.findStorageListByEmail(email);
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

	@GetMapping("/followLatest")
	@ApiOperation(value = "팔로우한 사용자들의 최신 글 리스트")
	public ResponseEntity<List<PostList>> followLatest(@RequestParam(required = true) final String email) throws Exception {
		List<PostList> list = postListRepository.findLatestMyFollowPost(email);
		for(PostList post : list) {
			User user = userRepository.getUserByEmail(post.getWriter());	//작성자의 user 정보
			post.setNickname(post.getNickname());
			post.setProfile(user.getProfile());
			post.setScore(user.getScore());
			if(goodRepository.isGood(email, post.getId()) > 0) post.setIsgood(true);
		}		
		return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
	}

	@GetMapping("popular")
	@ApiOperation(value = "인기게시물 로직 : 좋아요(1) + 조회(1) + 댓글(2) + 공유(2)")
	public ResponseEntity<List<PostList>> getPoularPost(@RequestParam(required = false) String email) {
		List<PopularScore> baseScore = algoRepository.getPopularScore();
		List<PopularScore> commentScore = algoRepository.getCommentScore();
		PriorityQueue<PopularScore> pq = new PriorityQueue<>(new Comparator<PopularScore>() {
			@Override
			public int compare(PopularScore o1, PopularScore o2) {
				return Long.compare(o2.getScore(), o1.getScore());
			}
		});
		
		/* 좋아요 + 조회수 점수 등록  */
		Map<Long, Long> map = new HashMap<>();
		for(PopularScore ps : baseScore) map.put(ps.getPostid(), ps.getScore());
		
		/* 댓글 점수 추가(댓글은 2점) */
		for(PopularScore ps : commentScore) {
			if(map.containsKey(ps.getPostid())) map.put(ps.getPostid(), map.get(ps.getPostid()) + ps.getScore() * 2); //있던 Post면 점수 더해줌
			else map.put(ps.getPostid(), ps.getScore() * 2);	//없던 Post면 새로 추가해줌
		}
		
		/* 점수 순대로 정렬 */
		for(Entry<Long, Long> cur : map.entrySet()) pq.add(new PopularScore(cur.getKey(), cur.getValue()));
		
		List<PopularScore> scoreList = new ArrayList<>();
		while(!pq.isEmpty()) scoreList.add(pq.poll());
		
		List<PostList> list = new ArrayList<>();	// 게시글 목록을 담을 List
		for(PopularScore ps : scoreList) list.add(postListRepository.searchPostById(ps.getPostid()));
		
		
		/* 좋아요 유무 표시 */
		if(email == null || email.equals("")) {	//비회원
			System.out.println("빈칸");
			return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
		}
		else {	//회원
			System.out.println("안빈칸");
			for(int i=0;i<list.size();i++) if(list.get(i) != null && goodRepository.isGood(email, list.get(i).getId()) > 0) list.get(i).setIsgood(true);
			return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
		}
	}
	
	@GetMapping("recommend")
	@ApiOperation(value = "추천게시물 리스트")
	public ResponseEntity<List<PostList>> getRecommendPost(@RequestParam String email) {
		List<Long> postList = algoRepository.getLookUp(email);	// 해당 user가 조회했던 게시물 목록
		Map<Long, Long> score = new HashMap<>();
		for(Long postid : postList) if(algoRepository.getPopularScoreByPostid(postid) != null) {
			score.put(postid, algoRepository.getPopularScoreByPostid(postid));	//좋아요, 조회수 점수 등록
		}
			
		for(Long postid : score.keySet()) score.put(postid, score.get(postid) + algoRepository.getCommentScoreByPostid(postid)); //댓글 점수 추가
		
		Map<String, Long> HashScore = new HashMap<>();
		for(Long postid : score.keySet()) {
			List<String> hashtagList = algoRepository.getHashtagByPostid(postid);	
			for(String hashtag : hashtagList) {	//게시물에 포함된 태그마다
				if(HashScore.containsKey(hashtag)) HashScore.put(hashtag, HashScore.get(hashtag) + score.get(postid));
				else HashScore.put(hashtag, score.get(postid));
			}
		}
		PriorityQueue<Object[]> pq = new PriorityQueue<>(new Comparator<Object[]>() {
			@Override
			public int compare(Object[] o1, Object[] o2) {
				return Long.compare((Long)o2[1], (Long)o1[1]);
			}
			
		});
		for(String hashtag : HashScore.keySet()) pq.add(new Object[] {hashtag, HashScore.get(hashtag)});	// 정렬
		
		Map<Long, Boolean> check = new HashMap<>();	 // postid 중복 체크
		List<PostList> resultList = new ArrayList<>();
		for(int i=0;i<5;i++) {
			if(pq.isEmpty()) break;
			List<Long> list = algoRepository.getPostidByTag((String)pq.poll()[0]);	//태그를 가진 postid list
			for(Long postid : list) {
				if(!check.containsKey(postid)) {
					check.put(postid, true);
					resultList.add(postListRepository.searchPostById(postid));
				}
			}
		}
		//좋아요 여부 체크
		for(int i=0;i<resultList.size();i++)
			if(resultList.get(i).getId() != null && algoRepository.getPopularScoreByPostid(resultList.get(i).getId()) != null && goodRepository.isGood(email, resultList.get(i).getId()) > 0) // 여기서 null
				resultList.get(i).setIsgood(true);
		return new ResponseEntity<List<PostList>>(resultList, HttpStatus.OK); 
	}
	
	@GetMapping("popularTag")
	@ApiOperation("인기태그 리스트 출력")
	public ResponseEntity<List<TagScore>> searchPopularTag() {
		return new ResponseEntity<List<TagScore>>(tagScoreRepository.searchTagScore(), HttpStatus.OK);
	}

//	@GetMapping("/followPopular")
//	@ApiOperation(value = "팔로우한 사용자들의 인기 글 리스트")
//	public ResponseEntity<List<Post>> followPopular(@RequestParam(required = true) final String email) throws Exception {
//		return new ResponseEntity<List<Post>>(postService.findFollowPopularByUser(email), HttpStatus.OK);
//	}
}