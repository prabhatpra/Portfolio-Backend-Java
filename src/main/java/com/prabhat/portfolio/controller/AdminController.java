package com.prabhat.portfolio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabhat.portfolio.constants.Constants;
import com.prabhat.portfolio.contact.dto.ApiResponseDto;
import com.prabhat.portfolio.contact.dto.ReplyRequestDto;
import com.prabhat.portfolio.contact.dto.ResponseDto;
import com.prabhat.portfolio.service.ContactService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Constants.ADMIN_BASE_PATH)
@Slf4j
@RequiredArgsConstructor
public class AdminController {

	private final ContactService contactService;
	
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

	    @PatchMapping("/{id}/read")
	    public ResponseEntity<ApiResponseDto> markAsRead(
	            @PathVariable("id") Long id) {

	        log.info("PATCH /contacts/{}/read API called", id);

	        ApiResponseDto response = contactService.markAsRead(id);

	        log.info("PATCH / contacts/{}/read completed successfully", id);

	        return ResponseEntity.ok(response);
	    }
	    
	    @PostMapping("/{id}/reply")
	    public ResponseEntity<ApiResponseDto> replyContact(
	    		@PathVariable("id") Long id, @Valid @RequestBody ReplyRequestDto request){
	    	
	    	log.info("POST /contacts/{}/reply API called", id);
	    	
	    	ApiResponseDto response = contactService.replyContact(id, request);
	    	
	    	log.info("POST /contacts/{}/reply completed successfully", id);
	    	
	    	return ResponseEntity.ok(response);
	    }
	}
