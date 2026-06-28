package com.prabhat.portfolio.service;

import com.prabhat.portfolio.dto.auth.AdminResponse;
import com.prabhat.portfolio.dto.auth.LoginRequest;
import com.prabhat.portfolio.dto.auth.RegisterRequest;
import com.prabhat.portfolio.dto.auth.RegisterResponseDto;

public interface AdminService {
	
	 RegisterResponseDto register(RegisterRequest request);

	 AdminResponse login(LoginRequest loginRequest);
		
	}
	
	
