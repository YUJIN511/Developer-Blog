package com.papple.blog.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LookUpPK implements Serializable {

	@NotBlank
	private String email;
	
	@NotBlank
	private Long postid;
}
