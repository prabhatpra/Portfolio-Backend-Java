package com.prabhat.portfolio.exception;

import com.prabhat.portfolio.constant.ErrorCodeEnum;

public class RateLimitException extends ContactException {

	public RateLimitException() {
		super(
				ErrorCodeEnum.RATE_LIMIT_EXCEEDED.getErrorCode(),
				ErrorCodeEnum.RATE_LIMIT_EXCEEDED.getErrorMessage(),
				ErrorCodeEnum.RATE_LIMIT_EXCEEDED.getHttpStatus()
				);
		
	}
}
