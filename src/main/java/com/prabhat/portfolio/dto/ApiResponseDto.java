package com.prabhat.portfolio.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseDto {

	private boolean success;
	private String message;
}
