package com.prabhat.portfolio.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prabhat.portfolio.auth.dto.AdminResponse;
import com.prabhat.portfolio.auth.dto.LoginRequest;
import com.prabhat.portfolio.auth.dto.RegisterRequest;
import com.prabhat.portfolio.entity.Admin;
import com.prabhat.portfolio.repository.AdminRepository;
import com.prabhat.portfolio.security.JwtService;
import com.prabhat.portfolio.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImple implements AdminService {

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	@Override
	public AdminResponse register(RegisterRequest request) {
		
		log.info("Register request received for email: {}", request.getEmail());
		
		if(adminRepository.findByEmail(request.getEmail()).isPresent()) {
			log.warn("Email already exists: {}", request.getEmail());
			throw new RuntimeException("Email already exists");
		}
		
		Admin admin = Admin.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role("ROLE_ADMIN")
				.build();
		
		Admin savedAdmin = adminRepository.save(admin);
		
		log.info("Admin registered successfully with id: {}", savedAdmin.getId());
		
		return AdminResponse.builder()
				.id(savedAdmin.getId())
				.email(savedAdmin.getEmail())
				.role(savedAdmin.getRole())
				.build();
	}
	
	@Override
	public AdminResponse login(LoginRequest loginRequest) {
		
		log.info("Login request received for email: {}", loginRequest.getEmail());
		
		Admin admin = adminRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> {
					log.error("Admin not found with email: {}", loginRequest.getEmail());
				 return new RuntimeException("Admin not found");
				});
		
		if(!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
			log.warn("Invalid password for email: {}", loginRequest.getEmail());
			throw new RuntimeException("Invalid Password");
		}
		
		admin.setLastLogin(LocalDateTime.now());
		adminRepository.save(admin);
		
		log.info("Admin logged in successfully: {}", admin.getEmail());
		
		String token = jwtService.generateToken(admin.getEmail(), admin.getRole());
		
		return AdminResponse.builder()
				.id(admin.getId())
				.email(admin.getEmail())
				.role(admin.getRole())
				.token(token)
				.lastLogin(admin.getLastLogin())
				.build();
	}
}
