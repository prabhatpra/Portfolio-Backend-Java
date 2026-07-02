package com.prabhat.portfolio.contact.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class RequestDto {

	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 50, message = "Name must be between 2 to 50 characters")
	private String name;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Size(max = 150, message = "Email must not exceed 150 characters")
	private String email;
	
	@NotBlank(message = "Subject is required")
	@Size(min = 5, max = 200, message = "subject must be 5 to 200 characters")
	private String subject;
	
	@NotBlank(message = "Message is required")
	@Size(min = 10, max = 1000, message = "Message must be 10-1000 characters")
	private String message;
	
}
