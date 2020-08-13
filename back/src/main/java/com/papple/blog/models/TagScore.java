package com.papple.blog.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class TagScore {
	@Id
	private String tagname;
	
	private Long score;
	
	public TagScore() {
		this.score = 0l;
	}
}
