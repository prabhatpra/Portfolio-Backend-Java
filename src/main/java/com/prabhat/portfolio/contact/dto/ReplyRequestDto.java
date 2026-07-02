package com.prabhat.portfolio.contact.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequestDto {

	@NotBlank(message = "Reply message is required")
	@Size(max = 5000, message = "Reply message must be not exceed 5000 characters")
	private String replyMessage;
}
