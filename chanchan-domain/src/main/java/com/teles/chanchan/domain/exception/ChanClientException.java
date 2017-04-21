package com.teles.chanchan.domain.exception;

public class ChanClientException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ChanClientException() {
		super();
	}

	public ChanClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChanClientException(String message) {
		super(message);
	}

	public ChanClientException(Throwable cause) {
		super(cause);
	}

}