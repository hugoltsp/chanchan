package com.hugoltsp.chanchan.exception;

public class ImageDownloadException extends RuntimeException {

	private static final long serialVersionUID = -2383240145996366767L;

	public ImageDownloadException() {
		super();
	}

	public ImageDownloadException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageDownloadException(String message) {
		super(message);
	}

	public ImageDownloadException(Throwable cause) {
		super(cause);
	}

}
