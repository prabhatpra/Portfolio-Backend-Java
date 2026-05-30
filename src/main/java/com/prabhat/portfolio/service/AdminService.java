package com.prabhat.portfolio.service;

import com.prabhat.portfolio.auth.dto.AdminResponse;
import com.prabhat.portfolio.auth.dto.LoginRequest;
import com.prabhat.portfolio.auth.dto.RegisterRequest;

public interface AdminService {
	
	public AdminResponse register(RegisterRequest request);

	public AdminResponse login(LoginRequest loginRequest);
		
	}
	
	
