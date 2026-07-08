package com.prabhat.portfolio.dto.contact;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import com.prabhat.portfolio.enums.ContactStatus;

@Data
@Builder
public class ResponseDto {

    private Long id;
    private String name;
    private String email;
    private String subject;
    private String message;
    private ContactStatus status;
    private LocalDateTime createdAt;
}