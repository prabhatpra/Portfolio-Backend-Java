package com.prabhat.portfolio.util;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.prabhat.portfolio.constants.Constants;
import com.prabhat.portfolio.entity.Contact;
import com.prabhat.portfolio.enums.EmailStatus;
import com.prabhat.portfolio.repository.ContactRepository;
import com.prabhat.portfolio.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailRetryScheduler {

	private final ContactRepository repository;
	private final EmailService emailService;
	
	@Scheduled(fixedDelay = Constants.EMAIL_RETRY_DELAY) // Every 10 minutes
	public void retryFailedEmail() {
		log.info("Email retry scheduler started");
		
		List<Contact> pendingContact = repository.findByEmailStatusAndEmailRetryCountLessThan(
				EmailStatus.PENDING,
				Constants.MAX_EMAIL_RETRY
			);
		if(pendingContact.isEmpty()) {
			log.debug("No failed emails found for retry");
			return;
		}
		
		log.info("Found {} failed emails for retry", pendingContact.size());
		
		for (Contact contact : pendingContact) {
			emailService.sendContactMail(contact);
		}
		
		log.info("Email retry scheduler completed.");
	}
}
