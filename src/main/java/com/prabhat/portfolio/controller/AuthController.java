package com.prabhat.portfolio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabhat.portfolio.auth.dto.AdminResponse;
import com.prabhat.portfolio.auth.dto.LoginRequest;
import com.prabhat.portfolio.auth.dto.RegisterRequest;
import com.prabhat.portfolio.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AdminService adminService;
	
	@PostMapping("/register")
	public ResponseEntity<AdminResponse> register(@RequestBody RegisterRequest registerRequest){
		
		log.info("Register API called for email: {}", registerRequest.getEmail());
		
		AdminResponse response = adminService.register(registerRequest);
		
		log.info("Register API completed successfully");
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AdminResponse> login(@RequestBody LoginRequest loginRequest){
		
		log.info("Login API called for email: {}", loginRequest.getEmail());
		
		AdminResponse response = adminService.login(loginRequest);
		
		log.info("Login API completed successfully");
		
		return ResponseEntity.ok(response);
	}
}
