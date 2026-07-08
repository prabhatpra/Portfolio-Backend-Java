package com.prabhat.portfolio.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

	@NotBlank(message = "userName is required")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	@Pattern(
		    regexp = "^[a-zA-Z0-9_]+$",
		    message = "Username can contain only letters, numbers and underscore"
		)
	private String userName;
	
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max=64, message = "Password must be between 8 and 64 characters")
    @Pattern(
    	    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,64}$",
    	    message = "Password must contain uppercase, lowercase, number and special character"
    	)
    private String password;
    
    @NotBlank(message = "Confirm Password is required")
    @Size(min = 8, max = 64, message = " confirm Password must be between 8 and 64 characters")
    private String confirmPassword;
    
    @Size(max = 100, message = "Admin code must not exceed 100 characters")
    private String adminCode;
}