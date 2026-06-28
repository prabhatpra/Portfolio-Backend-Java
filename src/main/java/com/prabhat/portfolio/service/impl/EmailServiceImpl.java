package com.prabhat.portfolio.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.prabhat.portfolio.constants.Constants;
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
    
    @Value("${portfolio.admin.email}")
    private String adminEmail;
    
    @Value("${resend.from.email}")
    private String fromEmail;

    @Async
    @Override
    public void sendContactMail(String name, String email, String subject, String message) {

        log.info("Preparing admin email for user: {}", email);

        try {
            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(fromEmail)
                    .to(adminEmail)
                    .replyTo(email)
                    .subject(String.format(
                            Constants.SUBJECT_FORMAT,
                            subject,
                            email
                    ))
                    .text(buildAdminMessage(name, email, subject, message))
                    .build();

    var response = resend.emails().send(params);

            log.info("Admin email sent successfully for user: {}", email);

        } catch (Exception e) {
            log.error("Failed to send admin email for user: {}", email, e);
        }
    }

    private String buildAdminMessage(String name, String email, String subject, String message) {
        return String.format(
                Constants.BODY_FORMAT,
                name,
                email,
                subject,
                message
        );
    }
}