package com.papple.blog.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import com.papple.blog.models.Post;
import com.papple.blog.models.Storage;
import com.papple.blog.models.StoragePK;
import com.papple.blog.models.User;
import com.papple.blog.repository.HistoryRepository;
import com.papple.blog.repository.StorageRepository;
import com.papple.blog.repository.UserRepository;
import com.papple.blog.security.services.HashtagService;
import com.papple.blog.security.services.PostService;

// http://localhost:8080/swagger-ui.html
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
	@ApiOperation(value = "새 글 게시")
	public ResponseEntity<String> insert(@RequestBody Post post, HashtagList hashtag) {
		System.out.println("새 글 게시");
		Post p = postService.save(post);
		for(int i=0;i<hashtag.getHashtagList().size();i++) {
			Hashtag ht = new Hashtag(new HashtagPK(p.getId(), hashtag.getHashtagList().get(i)));
			hashtagService.save(ht);
		}
		
		if(p != null) return new ResponseEntity<>("success", HttpStatus.OK);
		return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
	}

	@PutMapping("/upload")
	@ApiOperation(value = "post 대표 사진 업로드 / Encoding 호환문제로 새 글 게시와 한번에 불가능")
	public ResponseEntity<String> fileUpload(@RequestParam("filename") MultipartFile mFile, @RequestParam Long id, HttpServletRequest request){
//		웹서비스 경로 지정(로컬에서 사용시 이 코드 사용)
//		String root_path = request.getSession().getServletContext().getRealPath("/");
//		String attach_path = "resources/postRep/";
//		String final_path = root_path + attach_path + mFile.getOriginalFilename();
		//서버에서 돌릴 때는 해당 코드 사용
		String final_path = "/home/ubuntu/s03p13a604/back/src/main/webapp/resources/postRep/" + mFile.getOriginalFilename();
		System.out.println(final_path);
		try {
			mFile.transferTo(new File(final_path));
			postService.updatePicture(final_path, id);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("파일 업로드 실패");
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("/unupload")
	@ApiOperation(value = "post 대표 사진 지우기, 사진이 없을 때는 picture column null임.")
	public ResponseEntity<String> fileUnUpload(Long id) {
		postService.deletePicture(id);
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

				if(!newPost.getWriter().equals(email)){	// post 작성자의 글은 보관함 반영 X
					// 보관함에 담기
					Storage storage = new Storage(new StoragePK(email, id));
					storageRepository.save(storage);
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
	@ApiOperation(value = "포스트 삭제 - 보관함, 기록, 해시태그, 좋아요도 함께 삭제")
	public ResponseEntity<String> delete(Long id) {
		System.out.println("글 삭제");
		if(postService.findById(id) != null) {
			storageRepository.deleteByPostId(id);
			historyRepository.deleteByPostId(id);
			hashtagService.deleteHashtagByPostId(id);
			postService.deleteGoodByPostid(id);
			postService.deleteById(id);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
	}
}
