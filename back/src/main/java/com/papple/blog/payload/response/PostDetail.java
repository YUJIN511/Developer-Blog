package com.papple.blog.payload.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDetail {
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
    private List<String> tag;
    //좋아요 여부 - 비로그인 시 기본 false
    private boolean isgood;
    public PostDetail() {
		this.isgood = false;
	}
}
