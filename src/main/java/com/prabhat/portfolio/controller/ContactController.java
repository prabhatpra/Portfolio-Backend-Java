package com.prabhat.portfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prabhat.portfolio.constants.Constants;
import com.prabhat.portfolio.dto.ApiResponseDto;
import com.prabhat.portfolio.dto.RequestDto;
import com.prabhat.portfolio.dto.ResponseDto;
import com.prabhat.portfolio.enums.ContactStatus;
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

    @GetMapping
    public ResponseEntity<List<ResponseDto>> getAllContacts() {

        log.info("GET /contacts API called");

        List<ResponseDto> response = contactService.getAllContacts();

        log.info("GET /contacts completed. Total records: {}", response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getContactById(@PathVariable("id") Long id) {

        log.info("GET /contacts/{} API called", id);

        ResponseDto response = contactService.getContactById(id);

        log.info("GET /contacts/{} completed successfully", id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteContact(@PathVariable("id") Long id) {

        log.info("DELETE /contacts/{} API called", id);

        ApiResponseDto response = contactService.deleteContact(id);

        log.info("DELETE /contacts/{} completed successfully", id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponseDto> updateStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") ContactStatus status) {

        log.info("PATCH /contacts/{}/status called with status: {}", id, status);

        ApiResponseDto response = contactService.updateStatus(id, status);

        log.info("PATCH/ contacts/{}/status completed successfully. Status: {}", id, status);

        return ResponseEntity.ok(response);
    }
}