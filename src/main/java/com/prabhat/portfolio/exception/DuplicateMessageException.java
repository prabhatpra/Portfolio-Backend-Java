package com.prabhat.portfolio.exception;

import com.prabhat.portfolio.constant.ErrorCodeEnum;

public class DuplicateMessageException extends ContactException{

	public DuplicateMessageException() {
		super(
				ErrorCodeEnum.DUPLICATE_MESSAGE.getErrorCode(),
				ErrorCodeEnum.DUPLICATE_MESSAGE.getErrorMessage(),
				ErrorCodeEnum.DUPLICATE_MESSAGE.getHttpStatus()
				);
		
	}
}
