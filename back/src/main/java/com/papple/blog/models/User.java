package com.papple.blog.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Table(	name = "user", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "email"), 
			@UniqueConstraint(columnNames = "nickname")
		})
@Data
public class User {
	@Id
	private String email;

	@NotBlank
	private String password;
	
	private String nickname;

	private String profile;
	
	private Long score;
	
	private Integer usercertification;

	private String notification;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String email, String nickname, String password, Integer usercertification, Long score) {
		super();
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.score = score;
		this.usercertification = usercertification;
		this.notification = "111111";
		this.profile = "http://i3a604.p.ssafy.io/images/profile/pika.jpg";
	}

}
