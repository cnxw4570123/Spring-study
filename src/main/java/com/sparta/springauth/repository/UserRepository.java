package com.sparta.springauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sparta.springauth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from User u where u.username = :username")
	Optional<User> findByUsername(String username);

	@Query("select u from User u where u.email = :email")
	Optional<User> findByEmail(String email);
}
