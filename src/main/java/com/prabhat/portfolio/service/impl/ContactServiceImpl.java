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

        String email = request.getEmail() != null
                ? request.getEmail().trim().toLowerCase()
                : "";

        String message = request.getMessage() != null
                ? request.getMessage().trim()
                : "";

        log.info("Contact request received for email: {}", email);

        // 1. Rate limit check
        if (!rateLimiter.isAllowed(email)) {
            throw new RateLimitException();
        }

        // 2. Hourly limit check
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        long count = repository.countByEmailAndCreatedAtAfter(email, oneHourAgo);

        if (count >= ContactConstants.HOURLY_LIMIT) {
            throw new RateLimitException();
        }

        // 3. Duplicate check
        if (repository.existsByEmailAndMessage(email, message)) {
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
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
    

    @Override
    public ResponseDto getContactById(Long id) {

        Contact contact = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return mapToDto(contact);
    }

    @CacheEvict(value = ContactConstants.CONTACTS_CACHE, allEntries = true)
    @Override
    public ApiResponseDto deleteContact(Long id) {

        Contact contact = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        repository.delete(contact);

        return ApiResponseDto.builder()
                .success(true)
                .message("Deleted successfully")
                .build();
    }

    @CacheEvict(value = ContactConstants.CONTACTS_CACHE, allEntries =true)
    @Override
    public ApiResponseDto updateStatus(Long id, ContactStatus status) {

        Contact contact = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        contact.setStatus(status);
        repository.save(contact);
        
        return ApiResponseDto.builder()
        		.success(true)
        		.message("Status updated successfully")
        		.build();
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