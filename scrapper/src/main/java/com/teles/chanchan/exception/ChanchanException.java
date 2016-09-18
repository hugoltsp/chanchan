package com.teles.chanchan.exception;

public class ChanchanException extends RuntimeException {

	private static final long serialVersionUID = 1095160855449988553L;

	public ChanchanException() {
		super();
	}

	public ChanchanException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChanchanException(String message) {
		super(message);
	}

	public ChanchanException(Throwable cause) {
		super(cause);
	}

}
