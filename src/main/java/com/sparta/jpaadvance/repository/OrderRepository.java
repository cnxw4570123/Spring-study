package com.sparta.jpaadvance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.jpaadvance.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
