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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;

import com.papple.blog.models.Hashtag;
import com.papple.blog.models.HashtagList;
import com.papple.blog.models.HashtagPK;
import com.papple.blog.models.History;
import com.papple.blog.models.HistoryPK;
import com.papple.blog.models.Notification;
import com.papple.blog.models.Post;
import com.papple.blog.models.Storage;
import com.papple.blog.models.StoragePK;
import com.papple.blog.models.User;
import com.papple.blog.repository.HistoryRepository;
import com.papple.blog.repository.NotificationRepository;
import com.papple.blog.repository.StorageRepository;
import com.papple.blog.repository.UserRepository;
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

	@GetMapping("/all")
	@ApiOperation(value = "모든 포스트 보기")
	public ResponseEntity<List<Post>> searchAll() throws Exception {
		System.out.println("모든 포스트 출력");
		return new ResponseEntity<List<Post>>(postService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("writer/{writer}")
	@ApiOperation(value = "해당 이메일의 포스트 리스트 보기")
	public ResponseEntity<List<Post>> searchByEmail(@PathVariable String writer) throws Exception {
		System.out.println("해당 이메일의 포스트 출력");
		return new ResponseEntity<List<Post>>(postService.findByWriter(writer), HttpStatus.OK);
	}
	 
	@GetMapping("/postDetail")
	@ApiOperation(value = "해당 POST ID의 포스트 보기")
	public ResponseEntity<Post> searchById(@RequestParam(required = true) Long id, 
														@RequestParam(required = true) String email) throws Exception {
		System.out.println("해당 id의 포스트 출력");
		Post temp = postService.findById(id).get();

		if(!temp.getWriter().equals(email)){	// 포스트 작성자의 history, 조회수 반영 X
			temp.setViews(temp.getViews()+1);
			Post post = postService.save(temp);

			// history에 담기
			History history = new History(new HistoryPK(email, id));
			historyRepository.save(history);

			return new ResponseEntity<Post>(post, HttpStatus.OK);
		} else{
			return new ResponseEntity<Post>(temp, HttpStatus.OK);
		}
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
		return new ResponseEntity<List<Post>>(postService.findMyHashPost(hashtag, email), HttpStatus.OK);
	}	
	
	@GetMapping("myCategory/{email}")
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
	
	@GetMapping("search/{word}")
	@ApiOperation(value = "해당 word를 제목 또는 내용에 포함하고 있는 포스트 리스트 출력.")
	public ResponseEntity<List<Post>> searchByWord(@PathVariable String word) throws Exception {
		return new ResponseEntity<List<Post>>(postService.searchByWord(word), HttpStatus.OK);
	}
	
	@GetMapping("hashSearch/{word}")
	@ApiOperation(value = "해시태그로 게시물 검색")
	public ResponseEntity<List<Post>> searchByHashtag(@PathVariable String word) throws Exception {
		System.out.println("해시태그 검색");
		return new ResponseEntity<List<Post>>(postService.searchByHashtag(word), HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "새 글 게시 - 글 정보 + 파일의 접근경로 DB에 저장")
	public ResponseEntity<String> insert(@RequestBody Post post, HashtagList hashtag) {
		System.out.println("새 글 게시");  
//		System.out.println(post);
//		if(post.getPicture() != null && !post.getPicture().equals("")) {
//			System.out.println(post.getPicture());
//			String tem = post.getPicture().replace("/images", "+");
//			
//			StringTokenizer st = new StringTokenizer(tem, "+");
//			
//			String prev = st.nextToken();	// http://i3a604.p.ssafy.io
//			String next = st.nextToken();	// /dateString_mFile.getOriginalFilename()
//			
//			String path = "/home/ubuntu/s03p13a604/back/src/main/webapp/resources" + next;
//			
//			post.setPicture(path);
//			
//			
//		}
		
		Post p = postService.save(post);
		
		for(int i=0;i<hashtag.getHashtagList().size();i++) {
			Hashtag ht = new Hashtag(new HashtagPK(p.getId(), hashtag.getHashtagList().get(i)));
			hashtagService.save(ht);
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
	public ResponseEntity<String> modify(@RequestBody Post post, HashtagList hashtag) {
		System.out.println("글 수정");
		Optional<Post> tem = postService.findById(post.getId());
		if(tem != null) {
			tem.ifPresent(selectPost -> {
				selectPost.setTitle(post.getTitle());
				selectPost.setContent(post.getContent());
				selectPost.setPicture(post.getPicture());
				Post newPost = postService.save(selectPost);
				System.out.println(newPost);
			});
			
			hashtagService.deleteHashtagByPostId(post.getId());	//해당 글의 해시태그 모두 삭제
			for(int i=0;i<hashtag.getHashtagList().size();i++) {	//다시 생성
				Hashtag ht = new Hashtag(new HashtagPK(post.getId(), hashtag.getHashtagList().get(i)));
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

				if(!newPost.getWriter().equals(email)){	//자신의 글은 보관함, 알림 반영 X
					// 보관함에 담기
					Storage storage = new Storage(new StoragePK(email, id));
					storageRepository.save(storage);

					// 알람 발생
					String actionName = userRepository.getUserByEmail(email).getNickname();
					String targetName = userRepository.getUserByEmail(newPost.getWriter()).getNickname();
					Notification notification = Notification.builder()
								.message(actionName +"님이 "+ targetName +"님의 글에 좋아요를 누르셨습니다.")
								.actionuser(email)
								.targetuser(newPost.getWriter())
								.notiurl("http://localhost:8081/api/post/postDetail/"+id+"/"+email)
								.build();
					notificationService.save(notification);
				}
			});
			postService.insertGood(email, id);	//goodList 테이블에 좋아요 기록
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

				if(!newPost.getWriter().equals(email)){	// post 작성자의 글은 보관함 반영 X
					// 보관함에서 지우기
					storageRepository.deleteByEmailAndPostid(email, id);
				}
			});
			postService.deleteGood(email, id);	//goodList 테이블에 좋아요 삭제
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("isgood")
	@ApiOperation(value = "좋아요를 누른 게시물인지 true, false 문자열로 반환")
	public ResponseEntity<String> isGood(@RequestParam(required = true) Long id, @RequestParam(required = true) String email) {
		System.out.println("좋아요 확인");
		if(postService.isGood(email, id) > 0) return new ResponseEntity<String>("true", HttpStatus.OK);
		return new ResponseEntity<String>("false", HttpStatus.FORBIDDEN);
	}
	
	@DeleteMapping
	@ApiOperation(value = "포스트 삭제 - 보관함, 기록, 해시태그, 좋아요, 파일도 함께 삭제")
	public ResponseEntity<String> delete(Long id) {
		System.out.println("글 삭제");
		Optional<Post> post = postService.findById(id);
		if(post != null) {
			storageRepository.deleteByPostId(id);
			historyRepository.deleteByPostId(id);
			hashtagService.deleteHashtagByPostId(id);
			postService.deleteGoodByPostid(id);
			
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

}
