package com.prabhat.portfolio.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prabhat.portfolio.constants.Constants;
import com.prabhat.portfolio.dto.auth.AdminResponse;
import com.prabhat.portfolio.dto.auth.LoginRequest;
import com.prabhat.portfolio.dto.auth.RegisterRequest;
import com.prabhat.portfolio.dto.auth.RegisterResponseDto;
import com.prabhat.portfolio.entity.User;
import com.prabhat.portfolio.enums.Role;
import com.prabhat.portfolio.exception.DuplicateEmailException;
import com.prabhat.portfolio.exception.InvalidCredentialException;
import com.prabhat.portfolio.exception.PasswordMismatchException;
import com.prabhat.portfolio.repository.AdminRepository;
import com.prabhat.portfolio.security.JwtService;
import com.prabhat.portfolio.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AdminService {

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	
	@Value("${app.admin.secret.code}")
	private String adminSecretCode;

	@Transactional
	@Override
	public RegisterResponseDto register(RegisterRequest request) {

		log.info("Register request received for email: {}", request.getEmail());

		// Password match check
		if(!request.getPassword().equals(request.getConfirmPassword())) {
			throw new PasswordMismatchException();
		}

		// Email already exists check
		if(adminRepository.existsByEmail(request.getEmail())) {
			log.warn("Email already exists: {}", request.getEmail());
			throw new DuplicateEmailException();
		}

		
		Role userRole = Role.ROLE_USER;  // Default USER
		
		
		if(request.getAdminCode() != null && 
		   !request.getAdminCode().isEmpty() &&
		   request.getAdminCode().equals(adminSecretCode)) {
			userRole = Role.ROLE_ADMIN;  
			log.info("User registered as ADMIN");
		} else {
			log.info("User registered as USER");
		}

		
		User user = User.builder()
				.userName(request.getUserName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(userRole)  
				.build();

		User savedUser = adminRepository.save(user);

		log.info("User registered successfully with id: {} and role: {}", 
		         savedUser.getId(), savedUser.getRole());

		return RegisterResponseDto.builder()
				.id(savedUser.getId())
				.userName(savedUser.getUserName())
				.email(savedUser.getEmail())
				.role(savedUser.getRole().name())
				.message(Constants.REGISTER_SUCCESS)
				.build();
	}

	@Transactional
	@Override
	public AdminResponse login(LoginRequest loginRequest) {

		log.info("Login request received for email: {}", loginRequest.getEmail());

		User user = adminRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> {
					log.warn("Invalid login attempt for email: {}", loginRequest.getEmail());
				 return new InvalidCredentialException();
				});

		if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			log.warn("Invalid password for email: {}", loginRequest.getEmail());
			throw new InvalidCredentialException();
		}

		user.setLastLogin(LocalDateTime.now());
		adminRepository.save(user);

		log.info("User logged in successfully: {} with role: {}", 
		         user.getEmail(), user.getRole());

		
		String accessToken = jwtService.generateToken(user.getEmail(), user.getRole().name());

		return AdminResponse.builder()
				.id(user.getId())
				.userName(user.getUserName())
				.email(user.getEmail())
				.role(user.getRole().name())  
				.token(accessToken)
				.lastLogin(user.getLastLogin())
				.build();
	}
}