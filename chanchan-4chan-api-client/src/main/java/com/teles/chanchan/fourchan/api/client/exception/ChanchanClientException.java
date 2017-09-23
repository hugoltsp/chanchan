package com.teles.chanchan.fourchan.api.client.exception;

import com.teles.chanchan.domain.exception.ChanchanException;

public class ChanchanClientException extends ChanchanException {

	private static final long serialVersionUID = 1L;

	private Integer httpStatusCode;

	public ChanchanClientException() {
		super();
	}

	public ChanchanClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChanchanClientException(String message) {
		super(message);
	}

	public ChanchanClientException(Throwable cause) {
		super(cause);
	}

	public ChanchanClientException(Integer httpStatusCode, Throwable cause) {
		super(cause);
		this.httpStatusCode = httpStatusCode;
	}

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

}