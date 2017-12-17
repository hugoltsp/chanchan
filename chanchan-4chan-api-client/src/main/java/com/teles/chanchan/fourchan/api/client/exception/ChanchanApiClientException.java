package com.teles.chanchan.fourchan.api.client.exception;

import com.teles.chanchan.domain.exception.ChanchanException;

public class ChanchanApiClientException extends ChanchanException {

	private static final long serialVersionUID = 1L;

	private Integer httpStatusCode;

	public ChanchanApiClientException(Integer httpStatusCode, Throwable cause) {
		super(cause);
		this.httpStatusCode = httpStatusCode;
	}

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

}