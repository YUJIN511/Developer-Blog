package com.papple.blog.controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
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

import com.papple.blog.models.BlogConfig;
import com.papple.blog.models.Follow;
import com.papple.blog.models.Hashtag;
import com.papple.blog.models.HashtagPK;
import com.papple.blog.models.History;
import com.papple.blog.models.HistoryPK;
import com.papple.blog.models.Notification;
import com.papple.blog.models.Post;
import com.papple.blog.models.Storage;
import com.papple.blog.models.StoragePK;
import com.papple.blog.payload.response.HashtagList;
import com.papple.blog.payload.response.PopularScore;
import com.papple.blog.payload.response.PostDetail;
import com.papple.blog.payload.response.PostList;
import com.papple.blog.repository.ConfigRepository;
import com.papple.blog.repository.FollowRepository;
import com.papple.blog.repository.HistoryRepository;
import com.papple.blog.repository.PostAlgorithmRepository;
import com.papple.blog.repository.PostListRepository;
import com.papple.blog.repository.StorageRepository;
import com.papple.blog.repository.UserRepository;
import com.papple.blog.security.services.CommentService;
import com.papple.blog.security.services.FollowService;
import com.papple.blog.security.services.HashtagService;
import com.papple.blog.security.services.NotificationService;
import com.papple.blog.security.services.PostService;

// http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private PostService postService;
	@Autowired
	private HashtagService hashtagService;
	@Autowired
	private HistoryRepository historyRepository;
	@Autowired
	private StorageRepository storageRepository;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private PostListRepository postListRepository;
	@Autowired
	private FollowService followService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private PostAlgorithmRepository algoRepository;
	@Autowired
    private ConfigRepository configRepository;

	@GetMapping("/all")
	@ApiOperation(value = "모든 포스트 보기")
	public ResponseEntity<List<PostList>> searchAll(String email) throws Exception {
		System.out.println("모든 포스트 출력");
		List<PostList> list = postListRepository.searchAllPost();
		if(email == null || email.equals("")) {	//비회원
			System.out.println("빈칸");
			return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
		}
		else {	//회원
			System.out.println("안빈칸");
			for(int i=0;i<list.size();i++) if(storageRepository.isGood(email, list.get(i).getId()) > 0) list.get(i).setIsgood(true);
			return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
		}
	}
	
	@GetMapping("writer/{email}")
	@ApiOperation(value = "해당 이메일의 포스트 리스트 보기(나 혹은 다른 사람 블로그에서)")
	public ResponseEntity<List<PostList>> searchByEmail(@PathVariable String email) throws Exception {
		System.out.println("해당 이메일의 포스트 출력");
		List<PostList> list = postListRepository.searchByEmail(email);
		for(int i=0;i<list.size();i++) if(storageRepository.isGood(email, list.get(i).getId()) > 0) list.get(i).setIsgood(true);
		return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
	}
	 
	@GetMapping("/postDetail")
	@ApiOperation(value = "해당 POST ID의 포스트 보기 (email 값이 없으면 isgood은 false, 조회수 ++, email이 있으면 isgood 활성화, 조회수++, history 추가")
	public ResponseEntity<PostDetail> searchByIdAndEmail(@RequestParam(required = true) Long id, 
			@RequestParam(required = false) String email) throws Exception {
		System.out.println("해당 id의 포스트 출력");
		
		// detail + hashtag
		PostDetail detail = postListRepository.searchPostDetail(id);
		System.out.println(detail);
		List<String> tag = postListRepository.searchHashtag(id);
		detail.setTag(tag);
		
		if(email != null && !email.equals("")) {	//email이 있을 때만
			if(storageRepository.isGood(email, id) > 0) detail.setIsgood(true);
			// 조회 테이블에 추가(추천 게시물 관련)
			if(algoRepository.isRead(email, id) < 1) {	//이미 조회한 게시물이 아니라면, 조회 게시물에 insert
				algoRepository.insertRead(email, id);
			}
			
		}
		
		Post temp = postService.findById(id).get();		//조회수, history

		if(!temp.getWriter().equals(email)){	// 포스트 작성자의 history, 조회수 반영 X
			temp.setViews(temp.getViews()+1);
			Post post = postService.save(temp);
			
			// history에 담기
			if(email != null && !email.equals("")) {	//email이 있을 때만
				History history = new History(new HistoryPK(email, id));
				historyRepository.save(history);
				if(storageRepository.isGood(email, id) > 0) detail.setIsgood(true);
			}
		}
		
		//작성자의 blog 설정
		Optional<BlogConfig> bco = configRepository.findById(detail.getWriter());

		if(bco.isPresent()) {
			BlogConfig bc = bco.get();
			detail.setBlogName(bc.getName());
			detail.setBlogDescription(bc.getDescription());
			detail.setBlogPicture(bc.getPicture());
		}
		detail.setFollowerNum(followService.MyFollowerCnt(detail.getWriter()));
		detail.setFollow(followService.isFollow(email, detail.getWriter()) > 0 ? true : false);
		
		return new ResponseEntity<PostDetail>(detail, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "id로 해당 포스트 조회")
	public ResponseEntity<Post> searchById(@PathVariable Long id) {
		System.out.println("해당 id의 포스트 출력");
		Post post = postService.findById(id).get();
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}
	
	@GetMapping("postTag/{postid}")
	@ApiOperation(value = "해당 게시물의 해시태그 목록 출력")
	public ResponseEntity<List<String>> searchHashTag(@PathVariable Long postid) throws Exception {
		System.out.println("해당 게시물의 해시태그 목록 출력");
		List<Hashtag> hashList = hashtagService.findByPostid(postid);
		List<String> list = new ArrayList<>();
		for(int i=0;i<hashList.size();i++) {
			list.add(hashList.get(i).getHashtagPK().getHashtag());
		}
		return new ResponseEntity<List<String>>(list, HttpStatus.OK);
	}
	
	@GetMapping("my/{email}/{hashtag}")
	@ApiOperation(value = "내가 쓴 특정 해시태그의 글들을 출력(HashTag Category 안 게시물들) ")
	public ResponseEntity<List<Post>> searchHashTag(@PathVariable String hashtag, @PathVariable String email) throws Exception {
		System.out.println("내가 쓴 특정 해시태그의 글들을 출력");
		List<Post> list = postService.findMyHashPost(hashtag, email);
		for(Post post : list) post.setContent("");
		return new ResponseEntity<List<Post>>(list, HttpStatus.OK);
	}	
	
	@GetMapping("mycategory/{email}")
	@ApiOperation(value = "내가 쓴 글들의 HashTag 리스트 출력(Category) - 정렬됨")
	public ResponseEntity<List<String>> searchMyHashCategory(@PathVariable String email) throws Exception {
		System.out.println("내 Category 출력(정렬됨)");
		List<Hashtag> hashlist = hashtagService.myHashCategory(email);
		Set<String> s = new TreeSet();
		for(int i=0;i<hashlist.size();i++) s.add(hashlist.get(i).getHashtagPK().getHashtag());
		List<String> res = new ArrayList<String>();
		for(String hash : s) res.add(hash);
		return new ResponseEntity<List<String>>(res, HttpStatus.OK);
	}
	
	@GetMapping("mycategory/cnt")
	@ApiOperation(value = "나의 해시태그 카테고리의 글 개수 리턴")
	public ResponseEntity<Integer> searchMyHashCategoryCnt(String email, String hashtag) {
		return new ResponseEntity<Integer>(postService.cntCategory(email, hashtag), HttpStatus.OK);
	}
	
	@GetMapping("search/{word}")
	@ApiOperation(value = "해당 word를 제목 또는 내용에 포함하고 있는 포스트 리스트 출력.")
	public ResponseEntity<List<Post>> searchByWord(@PathVariable String word) throws Exception {
		List<Post> list = postService.searchByWord(word);
		for(Post post : list) post.setContent("");
		return new ResponseEntity<List<Post>>(list, HttpStatus.OK);
	}
	
	@GetMapping("hashSearch/{word}")
	@ApiOperation(value = "해시태그로 게시물 검색")
	public ResponseEntity<List<Post>> searchByHashtag(@PathVariable String word) throws Exception {
		System.out.println("해시태그 검색");
		List<Post> list = postService.searchByHashtag(word);
		for(Post post : list) post.setContent("");
		return new ResponseEntity<List<Post>>(list, HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "새 글 게시 - 글 정보 + 파일의 접근경로 DB에 저장")
	public ResponseEntity<String> insert(@RequestBody Post post, HashtagList tag) {
		System.out.println("새 글 게시");  
		
		Post p = postService.save(post);	// 글 저장
		
		for(int i=0;i<tag.getTag().size();i++) {
			Hashtag ht = new Hashtag(new HashtagPK(p.getId(), tag.getTag().get(i)));
			hashtagService.save(ht);
		}

		// 알림 발생(0100000)
		List<Follow> followerList = followService.findByFollowed(post.getWriter());	
		String actionName = userRepository.getUserByEmail(post.getWriter()).getNickname();
		for(Follow f : followerList){
			Notification notification = Notification.builder()
				.message(actionName +"님의 블로그에 새로운 게시물이 등록되었습니다. 가장 먼저 방문해 게시물을 확인해보세요.")
				.actionuser(post.getWriter())
				.targetuser(f.getFollowPK().getFollower())
				.notiurl("http://i3a604.p.ssafy.io/post/postDetail/"+post.getId())
				.build();
			
			notification.setType(1<<5);
			notificationService.save(notification);
		}
		if(p != null) return new ResponseEntity<>("success", HttpStatus.OK);
		return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
	}

	@PutMapping("/upload")
	@ApiOperation(value = "post 대표 사진 업로드")
	public ResponseEntity<String> fileUpload(@RequestParam("filename") MultipartFile mFile, HttpServletRequest request){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowdate = new Date();
		String dateString = formatter.format(nowdate);	//현재시간 문자열
		
		String real_path = "/home/ubuntu/s03p13a604/back/src/main/webapp/resources/postRep/" + 
				dateString + "_" + mFile.getOriginalFilename();	//경로 + 날짜시간 + _ +파일이름으로 저장

		String access_path = "http://i3a604.p.ssafy.io/images/postRep/" + dateString + "_" + mFile.getOriginalFilename();
		
		try {
			mFile.transferTo(new File(real_path));	//실제경로로 파일을 저장
			return new ResponseEntity<String>(access_path, HttpStatus.OK);	//접근경로 return
		} catch (IOException e) {
			System.out.println("파일 업로드 실패");
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("/unupload")
	@ApiOperation(value = "post 대표 사진 지우기(picture column null로)")
	public ResponseEntity<String> fileUnUpload(Long id) {
		postService.deletePicture(id);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@DeleteMapping("/delfile")
	@ApiOperation(value = "서버에 저장된 사진 지우기")
	public ResponseEntity<String> fileDelete(String filePath) {
		String tem = filePath.replace("/postRep", "+");
		StringTokenizer st = new StringTokenizer(tem, "+");
		
		String prev = st.nextToken();	//http://i3a604.p.ssafy.io/images
		String next = st.nextToken();	///"/" + dateString + "_" + mFile.getOriginalFilename();
		
		String path = "/home/ubuntu/s03p13a604/back/src/main/webapp/resources/postRep" + next;
		
		File delFile = new File(path);
		if(delFile.exists()) delFile.delete();
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@PutMapping
	@ApiOperation(value = "포스트 수정 (+해시태그 수정 - 해당 글의 해시태그를 모두 지우고, 다시 생성하는 로직)")
	public ResponseEntity<String> modify(@RequestBody Post post, HashtagList tag) {
		System.out.println("글 수정");
		Optional<Post> tem = postService.findById(post.getId());
		if(tem != null) {
			tem.ifPresent(selectPost -> {
				selectPost.setTitle(post.getTitle());
				selectPost.setContent(post.getContent());
				selectPost.setPicture(post.getPicture());
				selectPost.setSummary(post.getSummary());
				Post newPost = postService.save(selectPost);
				System.out.println(newPost);
			});
			
			hashtagService.deleteHashtagByPostid(post.getId());	//해당 글의 해시태그 모두 삭제
			for(int i=0;i<tag.getTag().size();i++) {	//다시 생성
				Hashtag ht = new Hashtag(new HashtagPK(post.getId(), tag.getTag().get(i)));
				hashtagService.save(ht);
			}
			
			return new ResponseEntity<>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
	}	
	
	@PutMapping("good")
	@ApiOperation(value = "포스트 좋아요++ : 좋아요 정보 생성, 보관함에 추가")
	public ResponseEntity<String> incGood(@RequestParam(required = true) Long id, @RequestParam(required = true) String email) {
		System.out.println("좋아요 수 count++");
		Optional<Post> tem = postService.findById(id);
		if(tem != null) {
			tem.ifPresent(selectPost -> {
				selectPost.setGood(tem.get().getGood()+1);
				Post newPost = postService.save(selectPost);

				// 보관함에 담기
				Storage storage = new Storage(new StoragePK(email, id));
				storageRepository.save(storage);
				
				if(!newPost.getWriter().equals(email)){	//자신의 글은 보관함, 알림 반영 X
					// 알람 발생(0000001)
					// 이전에 좋아요 눌렀었었는지 확인	>>>  notiurl 주소 front로 추후 변경
					if(notificationService.findByActionuserAndPostidAndType(email, id, 1) == null){
						String actionName = userRepository.getUserByEmail(email).getNickname();
						Notification notification = Notification.builder()
									.message(actionName +"님이 회원님의 게시물을 좋아합니다.")
									.actionuser(email)
									.targetuser(newPost.getWriter())
									.notiurl("http://i3a604.p.ssafy.io/post/postDetail/"+id)
									.build();
						notification.setPostid(id);
						notification.setType(1<<0);
						notificationService.save(notification);
					}
				}
			});
			Storage storage = new Storage(new StoragePK(email, id));
			storageRepository.save(storage);	//storage 테이블에 좋아요 기록

			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("ungood")
	@ApiOperation(value = "포스트 좋아요-- : 좋아요 정보 삭제, 보관함에서 지우기")
	public ResponseEntity<String> decGood(@RequestParam(required = true) Long id, @RequestParam(required = true) String email) {
		System.out.println("좋아요 수 count--");
		Optional<Post> tem = postService.findById(id);
		if(tem != null) {
			tem.ifPresent(selectPost -> {
				selectPost.setGood(tem.get().getGood()-1);
				Post newPost = postService.save(selectPost);

				// 보관함에서 지우기
				storageRepository.deleteByEmailAndPostid(email, id);
				
//				if(!newPost.getWriter().equals(email)){	// post 작성자의 글은 보관함 반영 X
//					
//					
//				}
			});
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
	}
	
//	@GetMapping("isgood")
//	@ApiOperation(value = "좋아요를 누른 게시물인지 true, false 문자열로 반환")
//	public ResponseEntity<String> isGood(@RequestParam(required = true) Long id, @RequestParam(required = true) String email) {
//		System.out.println("좋아요 확인");
//		if(postService.isGood(email, id) > 0) return new ResponseEntity<String>("true", HttpStatus.OK);
//		return new ResponseEntity<String>("false", HttpStatus.FORBIDDEN);
//	}
	
	@DeleteMapping
	@ApiOperation(value = "포스트 삭제 - 보관함, 기록, 해시태그, 좋아요, 파일도 함께 삭제")
	public ResponseEntity<String> delete(Long id) {
		System.out.println("글 삭제");
		Optional<Post> post = postService.findById(id);
		if(post != null) {
			storageRepository.deleteByPostid(id);
			historyRepository.deleteByPostid(id);
			hashtagService.deleteHashtagByPostid(id);
			commentService.deleteByPostid(id);
			
			post.ifPresent(selectPost -> {
				String path = selectPost.getPicture();
				if(path != null && !path.equals("")) {
					String tem = path.replace("/postRep", "+");
					StringTokenizer st = new StringTokenizer(tem, "+");
					
					String prev = st.nextToken();	//http://i3a604.p.ssafy.io/images
					String next = st.nextToken();	///"/" + dateString + "_" + mFile.getOriginalFilename();
					
					String realpath = "/home/ubuntu/s03p13a604/back/src/main/webapp/resources/postRep" + next;
					
					File delFile = new File(realpath);
					if(delFile.exists()) delFile.delete();
				}
			});
			
			postService.deleteById(id);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
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
			for(int i=0;i<list.size();i++) if(list.get(i) != null && storageRepository.isGood(email, list.get(i).getId()) > 0) list.get(i).setIsgood(true);
			return new ResponseEntity<List<PostList>>(list, HttpStatus.OK);
		}
	}
	
	@GetMapping("recommend")
	@ApiOperation(value = "인기게시물 로직 : 좋아요(1) + 조회(1) + 댓글(2) + 공유(2)")
	public ResponseEntity<String> getRecommendPost(@RequestParam String email) {
		List<Long> postList = algoRepository.getLookUp(email);	// 해당 user가 조회했던 게시물 목록
		Map<Long, Long> score = new HashMap<>();
		for(Long postid : postList) score.put(postid, algoRepository.getPopularScoreByPostid(postid));	//좋아요, 조회수 점수 등록
		for(Long postid : score.keySet()) score.put(postid, score.get(postid) + algoRepository.getCommentScoreByPostid(postid)); //댓글 점수 추가
		//29 : dd, ff, 28 : aa, bb, dd
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
		for(String hashtag : HashScore.keySet()) pq.add(new Object[] {hashtag, HashScore.get(hashtag)});
		
		while(!pq.isEmpty()) {
			System.out.println(Arrays.toString(pq.poll()));
		}
		
		
		System.out.println(algoRepository.getHashtagByPostid(28l));
		return new ResponseEntity<String>("success", HttpStatus.OK); 
	}
}
