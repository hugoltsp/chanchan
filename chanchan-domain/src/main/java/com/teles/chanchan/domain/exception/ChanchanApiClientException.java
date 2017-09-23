package com.teles.chanchan.domain.exception;

public class ChanchanApiClientException extends ChanchanException {

	private static final long serialVersionUID = 1L;

	private Integer httpStatusCode;

	public ChanchanApiClientException() {
		super();
	}

	public ChanchanApiClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChanchanApiClientException(String message) {
		super(message);
	}

	public ChanchanApiClientException(Throwable cause) {
		super(cause);
	}

	public ChanchanApiClientException(Integer httpStatusCode, Throwable cause) {
		super(cause);
		this.httpStatusCode = httpStatusCode;
	}

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

}