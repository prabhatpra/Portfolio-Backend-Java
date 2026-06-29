package com.prabhat.portfolio.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDto {

	private Long id;
	private String userName;
	private String email;
	private String role;
	private String message;
}
