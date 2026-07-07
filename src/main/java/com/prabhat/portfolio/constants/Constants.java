package com.prabhat.portfolio.constants;

public final class Constants {

	private Constants() {
		
	}
	
	//Contact
	public static final int HOURLY_LIMIT = 3;
    public static final long TIME_WINDOW = 10 * 60 * 1000L;   
    public static final String CONTACTS_CACHE = "contacts";
    
    public static final long RATE_LIMITER_CLEANUP_INTERVAL = 60_000L;
    
 // Messages
    public static final String REGISTER_SUCCESS = "Registered successfully.";
    public static final String INVALID_CREDENTIALS = "Invalid email or password.";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists.";
    public static final String PASSWORD_MISMATCH = "Password and confirm password do not match.";
    
    // JWT
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    
    public static final String ROLE_CLAIM = "role";
    
 // Base API Paths
    public static final String  AUTH_BASE_PATH = "/api/auth";
    public static final String CONTACT_BASE_PATH = "/api/contacts";
    public static final String ADMIN_BASE_PATH = "/api/admin/contacts";
    
 // Auth Endpoints
    public static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/register";
    
    public static final String SUBJECT_FORMAT = "Portfolio Contact: %s (%s)";
    public static final String REPLY_SUBJECT_PREFIX = "Re: ";
    public static final String BODY_FORMAT = 
    		"Name: %s\n" +
            "Email: %s\n" +
    		"Subject: %s\n" +
            "Message: %s ";
    
    public static final String MESSAGE_SENT_SUCCESS = "Message sent successfully";
    public static final String DELETE_SUCCESS = "Deleted successfully";
    public static final String STATUS_UPDATED_SUCCESS = "Status updated successfully";
    public static final String REPLY_SENT_SUCCESS = "Reply sent successfully";
    public static final String CONTACT_NOT_FOUND = "Contact not found with id: ";
    public static final String CONTACT_MARKED_AS_READ = "Contact marked as read";
    
    //Scheduled
    public static final long EMAIL_RETRY_DELAY = 10 * 60 * 1000L;
    public static final int MAX_EMAIL_RETRY = 3;
}
