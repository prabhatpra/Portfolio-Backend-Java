package com.prabhat.portfolio.service.impl;

import org.springframework.scheduling.annotation.Async;

import org.springframework.stereotype.Service;

import com.prabhat.portfolio.constant.EmailConstants;
import com.prabhat.portfolio.service.EmailService;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final Resend resend;

    public EmailServiceImpl(Resend resend) {
        this.resend = resend;
    }

    @Async
    @Override
    public void sendContactMail(String name, String email, String subject, String message) {

        log.info("Preparing admin email for user: {}", email);

        try {
            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(EmailConstants.FROM_EMAIL)
                    .to(EmailConstants.ADMIN_EMAIL)
                    .replyTo(email)
                    .subject(String.format(
                            EmailConstants.SUBJECT_FORMAT,
                            subject,
                            email
                    ))
                    .text(buildAdminMessage(name, email, subject, message))
                    .build();

            resend.emails().send(params);

            log.info("Admin email sent successfully for user: {}", email);

        } catch (Exception e) {
            log.error("Failed to send admin email for user: {}", email, e);
        }
    }

    private String buildAdminMessage(String name, String email, String subject, String message) {
        return String.format(
                EmailConstants.BODY_FORMAT,
                name,
                email,
                subject,
                message
        );
    }
}