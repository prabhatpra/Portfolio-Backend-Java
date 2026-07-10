package com.prabhat.portfolio.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.prabhat.portfolio.constants.Constants;
import com.prabhat.portfolio.entity.Contact;
import com.prabhat.portfolio.enums.EmailStatus;
import com.prabhat.portfolio.repository.ContactRepository;
import com.prabhat.portfolio.service.EmailService;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final Resend resend;
    private final ContactRepository contactRepository;
    
    @Value("${portfolio.admin.email}")
    private String adminEmail;
    
    @Value("${resend.from.email}")
    private String fromEmail;

    @Async
    @Override
    public void sendContactMail(Contact contact) {

    	if (contact.getEmailStatus() == EmailStatus.SENT) {
    		log.info("Email already send for contact id={}", contact.getId());
    		return;
    	}
    	
        log.info("Preparing admin email for user: {}", contact.getEmail());
        try {
            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(fromEmail)
                    .to(adminEmail)
                    .replyTo(contact.getEmail())
                    .subject(String.format(
                            Constants.SUBJECT_FORMAT,
                            contact.getSubject(),
                            contact.getEmail()
                    ))
                    .text(buildAdminMessage(
                    		contact.getName(),
                    		contact.getEmail(), 
                    		contact.getSubject(), 
                    		contact.getMessage()
              		))
                    .build();

            resend.emails().send(params);
            contact.setEmailStatus(EmailStatus.SENT);
            contact.setEmailError(null);
            log.info("Admin email sent successfully for user: {}", contact.getEmail());

        } catch (Exception e) {
        	int retryCount = contact.getEmailRetryCount() + 1;
        	contact.setEmailRetryCount(retryCount);
        	contact.setEmailError(e.getMessage());
        	
        	if(retryCount >= Constants.MAX_EMAIL_RETRY) {
        		contact.setEmailStatus(EmailStatus.FAILED);
        		log.error("Email permanently failed for contact id={} after {} retries.", contact.getId(), retryCount, e);
        	}
         else {
        	contact.setEmailStatus(EmailStatus.PENDING);
        	log.warn("Email sending failed for contact id={}. Retry {}/{}", contact.getId(), retryCount, Constants.MAX_EMAIL_RETRY, e);
        }
        
    }
        finally {
        	try {
        	contactRepository.save(contact);
        } catch (Exception ex) {
        	log.error("Failed to update email status for contact id={}", contact.getId(), ex);
          }
        }
    }

    
    @Async
    @Override
    public void sendReplyMail(String toEmail, String subject, String replyMessage) {

        log.info("Preparing reply email for user: {}", toEmail);

        try {
            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(fromEmail)
                    .to(toEmail)
                    .subject(Constants.REPLY_SUBJECT_PREFIX + subject)
                    .text(buildReplyMessage(replyMessage))
                    .build();
            resend.emails().send(params);
            log.info("Reply email sent successfully to user: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send reply email to user: {}", toEmail, e);
        }
    }
    
    private String buildAdminMessage(String name, String email, 
    		                   String subject, String message) {
    	return String.format(
    			Constants.BODY_FORMAT,
    			name,
    			email,
    			subject,
    			message
    			
    		);
    }
    
    private String buildReplyMessage(String replyMessage) {
    	return String.format("""
    
    Hello,
    
    %s
    
    Best Regards,
    Prabhat Prajapati
    
    This is a reply to your message submitted my portfolio website.
   	""", replyMessage);
    }
    
}