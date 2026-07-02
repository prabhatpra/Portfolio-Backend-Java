package com.prabhat.portfolio.service;

public interface EmailService {

	
    void sendContactMail(String name, String email, String subject, String message);
	
	void sendReplyMail(String toEmail, String subject, String replyMessage); 
}
