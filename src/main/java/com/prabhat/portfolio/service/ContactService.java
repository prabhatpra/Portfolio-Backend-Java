package com.prabhat.portfolio.service;

import java.util.List;

import com.prabhat.portfolio.dto.contact.ApiResponseDto;
import com.prabhat.portfolio.dto.contact.ReplyRequestDto;
import com.prabhat.portfolio.dto.contact.RequestDto;
import com.prabhat.portfolio.dto.contact.ResponseDto;

public interface ContactService {

	ApiResponseDto contactUser(RequestDto request);

	List<ResponseDto> getAllContacts();
	
	ResponseDto getContactById(Long id);
	
	ApiResponseDto deleteContact(Long id);
	
	ApiResponseDto markAsRead(Long id);
	
	ApiResponseDto replyContact(Long id, ReplyRequestDto request);
	

}
