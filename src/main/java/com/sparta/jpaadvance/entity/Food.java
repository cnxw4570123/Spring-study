package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "food")
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double price;

	@ManyToMany
	@JoinTable(name = "orders",
		joinColumns = @JoinColumn(name = "food_id"), // 현재 엔티티 입장에서의 조인할 컬럼
		inverseJoinColumns = @JoinColumn(name = "user_id") // 상대 엔티티 입장에서의 조인할 컬럼
	)
	private List<User> userList = new ArrayList<>();
}