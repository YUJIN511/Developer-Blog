package com.papple.blog.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table( name = "tempost")
@AllArgsConstructor
public class TemporaryPost {

	@Id
	private String writer;
	
	private String title;
	
	@Size(max = 100000)
	private String content;
	
	private String tag;
	
	private String picture;
	
	@Size(max = 10000)
	private String summary;
}
