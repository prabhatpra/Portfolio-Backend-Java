package com.prabhat.portfolio.exception;

import com.prabhat.portfolio.constant.ErrorCodeEnum;

public class NotFoundException extends ContactException {

	public NotFoundException(Long id) {
		super(
				ErrorCodeEnum.NOT_FOUND.getErrorCode(),
				"Contact not found with id: " +id,
				ErrorCodeEnum.NOT_FOUND.getHttpStatus()
				);
	}
}
