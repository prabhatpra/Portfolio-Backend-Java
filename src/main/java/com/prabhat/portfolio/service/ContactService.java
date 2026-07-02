package com.prabhat.portfolio.service;

import java.util.List;

import com.prabhat.portfolio.contact.dto.ApiResponseDto;
import com.prabhat.portfolio.contact.dto.ReplyRequestDto;
import com.prabhat.portfolio.contact.dto.RequestDto;
import com.prabhat.portfolio.contact.dto.ResponseDto;

public interface ContactService {

	ApiResponseDto contactUser(RequestDto request);

	List<ResponseDto> getAllContacts();
	
	ResponseDto getContactById(Long id);
	
	ApiResponseDto deleteContact(Long id);
	
	ApiResponseDto markAsRead(Long id);
	
	ApiResponseDto replyContact(Long id, ReplyRequestDto request);
	

}
