package com.prabhat.portfolio.dto.contact;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseDto {

	private boolean success;
	private String message;
	private Object data;
	private LocalDateTime timestamp;
}
