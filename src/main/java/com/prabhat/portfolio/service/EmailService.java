package com.prabhat.portfolio.service;

import com.prabhat.portfolio.entity.Contact;

public interface EmailService {

	
    void sendContactMail(Contact contact);
	
	void sendReplyMail(String toEmail, String subject, String replyMessage); 
}
