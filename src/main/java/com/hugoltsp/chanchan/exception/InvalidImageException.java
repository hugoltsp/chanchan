package com.hugoltsp.chanchan.exception;

public class InvalidImageException extends ChanchanException {

	private static final long serialVersionUID = 1902387282803071118L;

	public InvalidImageException() {
		super();
	}

	public InvalidImageException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidImageException(String message) {
		super(message);
	}

	public InvalidImageException(Throwable cause) {
		super(cause);
	}
	
}