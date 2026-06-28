package com.prabhat.portfolio.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prabhat.portfolio.constants.Constants;
import com.prabhat.portfolio.dto.auth.AdminResponse;
import com.prabhat.portfolio.dto.auth.LoginRequest;
import com.prabhat.portfolio.dto.auth.RegisterRequest;
import com.prabhat.portfolio.dto.auth.RegisterResponseDto;
import com.prabhat.portfolio.entity.Admin;
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
public class AdminServiceImpl implements AdminService {

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	@Transactional
	@Override
	public RegisterResponseDto register(RegisterRequest request) {
		
		log.info("Register request received for email: {}", request.getEmail());
		
		if(!request.getPassword().equals(request.getConfirmPassword())) {
			throw new PasswordMismatchException();
		}
		
		if(adminRepository.existsByEmail(request.getEmail())) {
			log.warn("Email already exists: {}", request.getEmail());
			throw new DuplicateEmailException();
		}
		
		Admin admin = Admin.builder()
				.userName(request.getUserName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.ROLE_ADMIN)
				.build();
		
		Admin savedAdmin = adminRepository.save(admin);
		
		log.info("Admin registered successfully with id: {}", savedAdmin.getId());
		
		return RegisterResponseDto.builder()
				.id(savedAdmin.getId())
				.email(savedAdmin.getEmail())
				.message(Constants.REGISTER_SUCCESS)
				.build();
	}
	@Transactional
	@Override
	public AdminResponse login(LoginRequest loginRequest) {
		
		log.info("Login request received for email: {}", loginRequest.getEmail());
		
		Admin admin = adminRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> {
					log.warn("Invalid login attempt for email: {}", loginRequest.getEmail());
				 return new InvalidCredentialException();
				});
		
		if(!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
			log.warn("Invalid password for email: {}", loginRequest.getEmail());
			throw new InvalidCredentialException();
		}
		
		admin.setLastLogin(LocalDateTime.now());
		adminRepository.save(admin);
		
		log.info("Admin logged in successfully: {}", admin.getEmail());
		
		String accessToken = jwtService.generateToken(admin.getEmail(), admin.getRole().name());
		
		
		return AdminResponse.builder()
				.id(admin.getId())
				.userName(admin.getUserName())
				.email(admin.getEmail())
				.role(admin.getRole().name())
				.token(accessToken)
				.lastLogin(admin.getLastLogin())
				.build();
	}
}
