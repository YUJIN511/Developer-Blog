package com.papple.blog.controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.lang.StackWalker.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.annotations.ApiOperation;

import com.papple.blog.email.MailHandler;
import com.papple.blog.email.TempKey;
import com.papple.blog.models.ERole;
import com.papple.blog.models.Post;
import com.papple.blog.models.Role;
import com.papple.blog.models.User;
import com.papple.blog.models.UserAuth;
import com.papple.blog.payload.request.LoginRequest;
import com.papple.blog.payload.request.SignupRequest;
import com.papple.blog.payload.request.UpdateRequest;
import com.papple.blog.payload.response.JwtResponse;
import com.papple.blog.payload.response.MessageResponse;
import com.papple.blog.repository.AuthRepository;
import com.papple.blog.repository.HistoryRepository;
import com.papple.blog.repository.HashtagRepository;
import com.papple.blog.repository.RoleRepository;
import com.papple.blog.repository.StorageRepository;
import com.papple.blog.repository.UserRepository;
import com.papple.blog.security.jwt.JwtUtils;
import com.papple.blog.security.services.FollowService;
import com.papple.blog.security.services.PostService;
import com.papple.blog.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	AuthRepository authRepository;
	@Autowired
	HistoryRepository historyRepository;
	@Autowired
	StorageRepository storageRepository;
	@Autowired
	PostService postService;
	@Autowired
	FollowService followService;
	@Autowired
	HashtagRepository hashtagRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	JavaMailSender mailSender;
 
	@PostMapping("/signin")
	@ApiOperation(value = "로그인")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		// 이메일 인증 받은 회원인지
		User userauth = userRepository.getUserByEmail(loginRequest.getEmail());
		if(userauth.getUsercertification() == 0){
			return ResponseEntity.badRequest().body("User authentication failed.");
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												userDetails.getEmail(), 
												 userDetails.getUsername(), 
												 roles));
	}

	
	@PostMapping("/signup")
	@ApiOperation(value = "회원 가입")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userRepository.existsById(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		
		// Create new user's account
		User user = new User(signUpRequest.getEmail(), null,
							encoder.encode(signUpRequest.getPassword()),0, 0l);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		sendMail(signUpRequest.getEmail());

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@Async
	public void sendMail(String email){
		try{
			final String key = new TempKey().getKey(50, false); // 인증키 생성
			UserAuth userauth = new UserAuth(email, key);
			authRepository.save(userauth);	// 인증키 DB저장
			MailHandler sendMail = new MailHandler(mailSender);
			sendMail.setSubject("[홈페이지 이메일 인증]"); // 메일제목
			sendMail.setText( // 메일내용
					"<h1>메일인증</h1>" + "<a href='http://i3a604.p.ssafy.io:8081/api/auth/emailConfirm?email=" + email + 
					"' target='_blenk'>이메일 인증 확인</a>");
			sendMail.setFrom("admin@gmail.com", "관리자"); // 보낸이
			sendMail.setTo(email); // 받는이
			sendMail.send();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	@GetMapping("/emailConfirm")
	@ApiOperation(value = "이메일 인증")
	public RedirectView emailConfirm(@RequestParam(required = true) final String email){
		Optional<UserAuth> userauth = authRepository.findById(email);
		if(userauth != null){
			userRepository.updateAuth(1, email);
			return new RedirectView("http://i3a604.p.ssafy.io/account/setNickname/"+email);
		}
		// 에러 페이지로 
		return null;
	}

	@GetMapping("/unregister")
	@ApiOperation(value = "회원탈퇴")
	public ResponseEntity<?> unregister(@RequestParam(required = true) final String email) {     
		historyRepository.deleteByEmail(email);			// 히스토리 삭제
		storageRepository.deleteByEmail(email);			// 보관함 삭제
		followService.deleteByEmail(email);				// 팔로우 삭제
		hashtagRepository.deleteHashtagByEmail(email);	// Hashtag 삭제
		postService.deleteGoodByEmail(email);			// 좋아요 삭제
		
		List<Post> postList= postService.findByWriter(email);
		for(Post post : postList) {	//해당 사용자가 작성했던 글 관련 데이터 삭제
			historyRepository.deleteByPostId(post.getId());
			storageRepository.deleteByPostId(post.getId());
			hashtagRepository.deleteHashtagByPostId(post.getId());
			postService.deleteGoodByPostid(post.getId());
		}
		postService.deleteByWriter(email);				// 쓴 글 삭제
		userRepository.deleteById(email);				// 회원 삭제
		
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
	} 

	@GetMapping("/userInfo")
	@ApiOperation(value = "회원정보 조회")
    public ResponseEntity<?> userInfo(@RequestParam(required = true) final String email) {
		User user = userRepository.getUserByEmail(email);
		// User user = userRepository.getOne(email); 
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/nicknameUpdate")
	@ApiOperation(value = "닉네임 업데이트")
	public ResponseEntity<?> nicknameUpdate(@RequestParam(required = true) final String nickname,
									@RequestParam(required = true) final String email) {

		if (userRepository.existsByNickname(nickname)) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Nickname is already taken!"));
		}
		userRepository.updateNickname(nickname, email);

        return ResponseEntity.ok(new MessageResponse("Nickname registered successfully!"));
	}
	
	@PostMapping("/nicknameUpdate")
	@ApiOperation(value="계정 설정 [닉네임]")
	public ResponseEntity<?> nicknameUpdate(@RequestBody User user){

		if (userRepository.existsByNickname(user.getNickname())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Nickname is already taken!"));
		}

		userRepository.updateNickname(user.getNickname(), user.getEmail());					// 닉네임 재설정
		return ResponseEntity.ok(new MessageResponse("Nickname updated successfully!"));
	}

	@PostMapping("/passwordUpdate")
	@ApiOperation(value="계정 설정 [비밀번호]")
	public ResponseEntity<?> passwordUpdate(@Valid @RequestBody UpdateRequest updateRequest){

		User user = userRepository.getUserByEmail(updateRequest.getEmail());

		// 사용자 인증
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(updateRequest.getEmail(), updateRequest.getPassword()));

		// 인증된 사용자인 경우 업데이트
		userRepository.resetPassword(encoder.encode(updateRequest.getNewpassword()), updateRequest.getEmail());	
		return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));

	}

	@GetMapping("/passwordEmail")
	@ApiOperation(value = "비밀번호 재설정 이메일")
	public ResponseEntity<?> passwordEmail(@RequestParam(required = true) final String email) {
		
		User user = userRepository.getUserByEmail(email);

		if(user != null){
			try{
				MailHandler sendMail = new MailHandler(mailSender);
				sendMail.setSubject("[비밀번호 재설정 이메일]"); // 메일제목

				sendMail.setText( // 메일내용
						"<h1>비밀번호 재설정</h1>" + 
						"<a href='http://i3a604.p.ssafy.io/account/resetpassword/" + email + "' target='_blenk'> 비밀번호 재설정 링크</a>");

						sendMail.setFrom("admin@gmail.com", "관리자"); // 보낸이
				sendMail.setTo(email); // 받는이
				sendMail.send();
			} catch(Exception e){
				e.printStackTrace();
			}
			return ResponseEntity.ok(new MessageResponse("Email sended successfully!"));
		} else{
			return ResponseEntity.badRequest().body("User eamil does not exist.");
		}
	}

	@PostMapping("/resetPassword")
	@ApiOperation(value = "비밀번호 재설정")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody LoginRequest loginRequest) {

		userRepository.resetPassword(encoder.encode(loginRequest.getPassword()), loginRequest.getEmail());

		return ResponseEntity.ok(new MessageResponse("Password registered successfully!"));
	}
	
	@PutMapping("/profile")
	@ApiOperation(value = "프로필 사진 업로드 - Access Path return")
	public ResponseEntity<String> fileUpload(@RequestParam("filename") MultipartFile mFile, @RequestParam String email, HttpServletRequest request) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowdate = new Date();
		String dateString = formatter.format(nowdate);	//현재시간 문자열
		
		String real_path = "/home/ubuntu/s03p13a604/back/src/main/webapp/resources/profile/" + 
				dateString + "_" + mFile.getOriginalFilename();	//경로 + 날짜시간 + _ +파일이름으로 저장

		String access_path = "http://i3a604.p.ssafy.io/images/profile/" + dateString + "_" + mFile.getOriginalFilename();

		try {
			mFile.transferTo(new File(real_path));
			userRepository.updateProfile(access_path, email);
			return new ResponseEntity<String>(access_path, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("파일 업로드 실패");
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		
	}
	
	@DeleteMapping("/delprofile")
	@ApiOperation(value = "서버에 있는 프로필 사진 파일을 삭제")
	public ResponseEntity<String> fileDelete(String filePath) {
		String tem = filePath.replace("/profile", "+");
		StringTokenizer st = new StringTokenizer(tem, "+");
		
		String prev = st.nextToken();	//http://i3a604.p.ssafy.io/images
		String next = st.nextToken();	///"/" + dateString + "_" + mFile.getOriginalFilename();
		
		String path = "/home/ubuntu/s03p13a604/back/src/main/webapp/resources/profile" + next;
		
		File delFile = new File(path);
		if(delFile.exists()) delFile.delete();
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@PutMapping("/unprofile")
	@ApiOperation(value = "프로필 사진 삭제, (사용자의 profile 컬럼을 null로)")
	public ResponseEntity<String> fileUnUpload(@RequestParam String email) {
		userRepository.deleteProfile(email);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

}

