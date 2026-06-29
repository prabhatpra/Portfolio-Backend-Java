package com.prabhat.portfolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prabhat.portfolio.entity.User;

public interface AdminRepository extends JpaRepository<User, Long> {

	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
}
