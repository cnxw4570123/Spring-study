package com.sparta.myselectshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.myselectshop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
