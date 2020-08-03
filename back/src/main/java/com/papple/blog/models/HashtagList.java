package com.papple.blog.models;

import java.util.List;

public class HashtagList {
	private List<String> hashtagList;

	public List<String> getHashtagList() {
		return hashtagList;
	}

	public void setHashtagList(List<String> hashtagList) {
		this.hashtagList = hashtagList;
	}

	public HashtagList(List<String> hashtagList) {
		super();
		this.hashtagList = hashtagList;
	}

	@Override
	public String toString() {
		return "HashtagList [hashtagList=" + hashtagList + "]";
	}

	
}
