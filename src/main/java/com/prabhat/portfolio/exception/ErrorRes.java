package com.prabhat.portfolio.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorRes {

   private String errorCode;
   private String message;
   private int status;
   private LocalDateTime timestamp;
}