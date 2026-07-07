package com.prabhat.portfolio.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prabhat.portfolio.entity.Contact;
import com.prabhat.portfolio.enums.ContactStatus;
import com.prabhat.portfolio.enums.EmailStatus;

public interface ContactRepository extends JpaRepository<Contact, Long> {

	
	
	 boolean existsByEmailAndMessage(String email, String message);
	
	 List<Contact> findByEmailStatusAndEmailRetryCountLessThan(EmailStatus emailStatus, int retryCount);
	
	 long countByEmailAndCreatedAtAfter(String email, LocalDateTime time);
	
	
	 List<Contact> findByEmailOrderByCreatedAtDesc(String email);
	 
	 Page<Contact> findByStatus(ContactStatus status, Pageable pageable);
	
	List<Contact> findByStatus(ContactStatus status);
	
	Page<Contact> findAll(Pageable pageable);

	
	}
