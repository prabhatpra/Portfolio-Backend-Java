package com.prabhat.portfolio.service;

import com.prabhat.portfolio.contact.dto.auth.AdminResponse;
import com.prabhat.portfolio.contact.dto.auth.LoginRequest;
import com.prabhat.portfolio.contact.dto.auth.RegisterRequest;
import com.prabhat.portfolio.contact.dto.auth.RegisterResponseDto;

public interface AdminService {
	
	 RegisterResponseDto register(RegisterRequest request);

	 AdminResponse login(LoginRequest loginRequest);
		
	}
	
	
