package com.sparta.jpaadvance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.jpaadvance.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
