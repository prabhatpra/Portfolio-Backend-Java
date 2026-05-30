package com.prabhat.portfolio.auth.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminResponse {

	private Long id;
	private String email;
	private String role;
	private String token;
	private LocalDateTime lastLogin;
}
