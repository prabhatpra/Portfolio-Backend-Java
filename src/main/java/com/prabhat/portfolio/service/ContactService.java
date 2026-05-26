package com.prabhat.portfolio.service;

import java.util.List;

import com.prabhat.portfolio.dto.ApiResponseDto;
import com.prabhat.portfolio.dto.RequestDto;
import com.prabhat.portfolio.dto.ResponseDto;
import com.prabhat.portfolio.enums.ContactStatus;

public interface ContactService {

	ApiResponseDto contactUser(RequestDto request);

	List<ResponseDto> getAllContacts();
	
	ResponseDto getContactById(Long id);
	
	ApiResponseDto deleteContact(Long id);
	
	ApiResponseDto updateStatus(Long id, ContactStatus status);

}
