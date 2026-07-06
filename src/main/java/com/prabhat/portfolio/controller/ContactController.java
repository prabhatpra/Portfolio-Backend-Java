package com.prabhat.portfolio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabhat.portfolio.constants.Constants;
import com.prabhat.portfolio.contact.dto.ApiResponseDto;
import com.prabhat.portfolio.contact.dto.RequestDto;
import com.prabhat.portfolio.service.ContactService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Constants.CONTACT_BASE_PATH)
@Slf4j
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;


    @PostMapping
    public ResponseEntity<ApiResponseDto> createContact(
            @Valid @RequestBody RequestDto requestDto) {

        log.info("POST /contacts API called for email: {}", requestDto.getEmail());

        ApiResponseDto response = contactService.contactUser(requestDto);

        log.info("POST /contacts completed successfully for email: {}", requestDto.getEmail());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}