package com.prabhat.portfolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prabhat.portfolio.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	
	Optional<Admin> findByEmail(String email);
	
	boolean existsByEmail(String email);
}
