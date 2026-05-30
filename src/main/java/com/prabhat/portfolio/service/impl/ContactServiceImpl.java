package com.prabhat.portfolio.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.prabhat.portfolio.constant.ContactConstants;
import com.prabhat.portfolio.dto.ApiResponseDto;
import com.prabhat.portfolio.dto.RequestDto;
import com.prabhat.portfolio.dto.ResponseDto;
import com.prabhat.portfolio.entity.Contact;
import com.prabhat.portfolio.enums.ContactStatus;
import com.prabhat.portfolio.exception.DuplicateMessageException;
import com.prabhat.portfolio.exception.NotFoundException;
import com.prabhat.portfolio.exception.RateLimitException;
import com.prabhat.portfolio.repository.ContactRepository;
import com.prabhat.portfolio.service.ContactService;
import com.prabhat.portfolio.service.EmailService;
import com.prabhat.portfolio.util.RateLimiter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {


    private final ContactRepository repository;
    private final RateLimiter rateLimiter;
    private final EmailService emailService;


    @CacheEvict(value = ContactConstants.CONTACTS_CACHE, allEntries = true)
    @Override
    public ApiResponseDto contactUser(RequestDto request) {

    	String email = normalize(request.getEmail()).toLowerCase();

    	String message = normalize(request.getMessage());

        log.info("Contact request received for email: {}", email);

        if (email.isBlank()) {
        	throw new IllegalArgumentException("Email connot be empty");
        }
        // 1. Rate limit check
        if (!rateLimiter.isAllowed(email)) {
        	log.warn("Rate limit exceeded for email: {}", email);
            throw new RateLimitException();
        }

        // 2. Hourly limit check
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        long count = repository.countByEmailAndCreatedAtAfter(email, oneHourAgo);

        if (count >= ContactConstants.HOURLY_LIMIT) {
        	log.warn("Hourly limit exceeded for email: {}", email);
            throw new RateLimitException();
        }

        // 3. Duplicate check
        if (repository.existsByEmailAndMessage(email, message)) {
        	log.warn("Duplicate message detected for email: {}", email);
            throw new DuplicateMessageException();
        }

        // 4. Save entity
        Contact contact = Contact.builder()
                .name(request.getName() != null ? request.getName().trim() : "")
                .email(email)
                .subject(request.getSubject() != null ? request.getSubject().trim() : "")
                .message(message)
                .status(ContactStatus.NEW)
                .build();

        Contact saved = repository.save(contact);

        log.info("Contact saved with id: {}", saved.getId());

        // 5. Email send
        emailService.sendContactMail(
                saved.getName(),
                saved.getEmail(),
                saved.getSubject(),
                saved.getMessage()
        );

        return ApiResponseDto.builder()
                .success(true)
                .message("Message sent successfully")
                .build();
    }

    @Cacheable(ContactConstants.CONTACTS_CACHE)
    @Override
    public List<ResponseDto> getAllContacts() {
    	
    	log.info("Fetching all contacts");
    	
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
    

    @Override
    public ResponseDto getContactById(Long id) {

    	log.info("Fetching contact by id={}", id);
    	
        Contact contact = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return mapToDto(contact);
    }

    @CacheEvict(value = ContactConstants.CONTACTS_CACHE, allEntries = true)
    @Override
    public ApiResponseDto deleteContact(Long id) {

    	log.info("Deleting contact with id: {}", id);
    	
        Contact contact = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        repository.delete(contact);

        log.info("Contact deleted successfully with id: {}", id);
        
        return ApiResponseDto.builder()
                .success(true)
                .message("Deleted successfully")
                .build();
    }

    @CacheEvict(value = ContactConstants.CONTACTS_CACHE, allEntries =true)
    @Override
    public ApiResponseDto updateStatus(Long id, ContactStatus status) {

    	log.info("Updating status for contact id: {}", id, status);
    	
        Contact contact = getContact(id);

        contact.setStatus(status);
        repository.save(contact);
        
        log.info("Status updated successfully for contact id: {}", id);
        
        return ApiResponseDto.builder()
        		.success(true)
        		.message("Status updated successfully")
        		.build();
    }

    private Contact getContact(Long id) {
    	return repository.findById(id)
    			.orElseThrow(() -> new NotFoundException(id));
    }
    
    private String normalize(String value) {
    	return value == null ? "" : value.trim();
    }
    
    private ResponseDto mapToDto(Contact c) {
        return ResponseDto.builder()
                .id(c.getId())
                .name(c.getName())
                .email(c.getEmail())
                .subject(c.getSubject())
                .message(c.getMessage())
                .status(c.getStatus())
                .createdAt(c.getCreatedAt())
                .build();
    }
}