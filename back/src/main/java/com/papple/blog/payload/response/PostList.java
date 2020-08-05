package com.papple.blog.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostList {
	private String email;
	private String nickname;
	private String profile;
	private Long score;
	private Long id;
	private String title;
	private String content;
	private String summary;
	private String picture;
	private String writer;
	private int good;
	private int views;
    private LocalDateTime createdate;
}
