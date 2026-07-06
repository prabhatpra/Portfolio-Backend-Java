package com.prabhat.portfolio.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prabhat.portfolio.constants.Constants;
import com.prabhat.portfolio.contact.dto.ApiResponseDto;
import com.prabhat.portfolio.contact.dto.ReplyRequestDto;
import com.prabhat.portfolio.contact.dto.RequestDto;
import com.prabhat.portfolio.contact.dto.ResponseDto;
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


    @CacheEvict(value = Constants.CONTACTS_CACHE, allEntries = true)
    @Transactional
    @Override
    public ApiResponseDto contactUser(RequestDto request) {

    	String name = normalize(request.getName());
    	
        String email = normalize(request.getEmail()).toLowerCase();

        String subject = normalize(request.getSubject());
        
    	String message = normalize(request.getMessage());

        log.info("Contact request received for email: {}", email);

        // 1. Rate limit check
        if (!rateLimiter.isAllowed(email)) {
        	log.warn("Rate limit exceeded for email: {}", email);
            throw new RateLimitException();
        }

        // 2. Hourly limit check
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        long count = repository.countByEmailAndCreatedAtAfter(email, oneHourAgo);

        if (count >= Constants.HOURLY_LIMIT) {
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
                .name(name)
                .email(email)
                .subject(subject)
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
                .message(Constants.MESSAGE_SENT_SUCCESS)
                .data(mapToDto(saved))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Cacheable(Constants.CONTACTS_CACHE)
    @Override
    public List<ResponseDto> getAllContacts() {
    	
    	log.info("Fetching all contacts");
    	
        List<ResponseDto> contacts = repository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
        
        log.info("fetched {} contacts", contacts.size());
        
        return contacts;
    }
    

    @Override
    public ResponseDto getContactById(Long id) {

    	log.info("Fetching contact by id={}", id);
    	

        return mapToDto(getContact(id));
    }

    @CacheEvict(value = Constants.CONTACTS_CACHE, allEntries = true)
    @Transactional
    @Override
    public ApiResponseDto deleteContact(Long id) {

    	log.info("Deleting contact with id: {}", id);
    	

        repository.delete(getContact(id));

        log.info("Contact deleted successfully with id: {}", id);
        
        return ApiResponseDto.builder()
                .success(true)
                .message(Constants.DELETE_SUCCESS)
                .timestamp(LocalDateTime.now())
                .build();
    }
    @Override
    @Transactional
    @CacheEvict(value = Constants.CONTACTS_CACHE, allEntries = true)
    public ApiResponseDto markAsRead(Long id) {
    	log.info("Marking contact as read. Id={}", id);
    	
    	Contact contact = getContact(id);
    	
    	contact.setStatus(ContactStatus.READ);
    	
    	repository.save(contact);
    	
    	return ApiResponseDto.builder()
                   .success(true)
                   .message(Constants.CONTACT_MARKED_AS_READ)
                   .timestamp(LocalDateTime.now())
                   .build();
    }

    
    @Override
    @Transactional
    @CacheEvict(value = Constants.CONTACTS_CACHE, allEntries = true)
    public ApiResponseDto replyContact(Long id, ReplyRequestDto request) {

        log.info("Reply request received for contact id={}", id);

        Contact contact = getContact(id);
        
        String replyMessage = normalize(request.getReplyMessage());
        
        emailService.sendReplyMail(
        		contact.getEmail(),
        		contact.getSubject(),
        		replyMessage
        		);

        contact.setReplyMessage(request.getReplyMessage());
        contact.setStatus(ContactStatus.REPLIED);
        contact.setRepliedAt(LocalDateTime.now());


        repository.save(contact);

        log.info("Reply sent successfully for contact id={}", id);

        return ApiResponseDto.builder()
                .success(true)
                .message(Constants.REPLY_SENT_SUCCESS)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    private Contact getContact(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(Constants.CONTACT_NOT_FOUND + id));
    }
    
    private String normalize(String value) {
    	return value == null ? "" : value.trim();
    }
    
    private ResponseDto mapToDto(Contact contact) {
    	return ResponseDto.builder()
    			.id(contact.getId())
    			.name(contact.getName())
    			.email(contact.getEmail())
    			.subject(contact.getSubject())
    			.message(contact.getMessage())
    			.status(contact.getStatus())
    			.createdAt(contact.getCreatedAt())
    			.build();
    }
}