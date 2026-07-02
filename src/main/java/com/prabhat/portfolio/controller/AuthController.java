package com.prabhat.portfolio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabhat.portfolio.constants.Constants;
import com.prabhat.portfolio.contact.dto.auth.AdminResponse;
import com.prabhat.portfolio.contact.dto.auth.LoginRequest;
import com.prabhat.portfolio.contact.dto.auth.RegisterRequest;
import com.prabhat.portfolio.contact.dto.auth.RegisterResponseDto;
import com.prabhat.portfolio.service.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(Constants.AUTH_BASE_PATH)
@RequiredArgsConstructor
public class AuthController {

	private final AdminService adminService;
	
	@PostMapping(Constants.REGISTER_PATH)
	public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequest registerRequest){
		
		log.info("Register API called for email: {}", registerRequest.getEmail());
		
		RegisterResponseDto response = adminService.register(registerRequest);
		
		log.info("Register successful for email: {}", registerRequest.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED)
				             .body(response);
	}
	
	@PostMapping(Constants.LOGIN_PATH)
	public ResponseEntity<AdminResponse> login(@Valid @RequestBody LoginRequest loginRequest){
		
		log.info("Login API called for email: {}", loginRequest.getEmail());
		
		AdminResponse response = adminService.login(loginRequest);
		
		log.info("Login successful for email: {}", loginRequest.getEmail());
		
		return ResponseEntity.ok(response);
	}
}
