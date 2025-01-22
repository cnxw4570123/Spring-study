package com.sparta.myselectshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role;

	private Long kakaoId;

	public static User ofUsernameAndPassword(String username, String password, String email, UserRoleEnum role) {
		return new UserBuilder()
			.username(username)
			.password(password)
			.email(email)
			.role(role)
			.build();
	}

	public static User ofKakao(String username, String password, String email, UserRoleEnum role, Long kakaoId) {
		return new UserBuilder()
			.username(username)
			.password(password)
			.email(email)
			.role(role)
			.kakaoId(kakaoId)
			.build();
	}

	public User kakaoIdUpdate(Long kakaoId) {
		this.kakaoId = kakaoId;
		return this;
	}
}