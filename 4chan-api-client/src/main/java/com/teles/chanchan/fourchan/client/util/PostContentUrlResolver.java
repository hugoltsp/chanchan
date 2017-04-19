package com.teles.chanchan.fourchan.client.util;

import com.teles.chanchan.domain.fourchan.FourchanPost;
import com.teles.chanchan.fourchan.client.exception.ChanClientException;

public class PostContentUrlResolver {

	private final String cdnUrl;

	public PostContentUrlResolver(String cdnUrl) throws IllegalArgumentException {
		if (cdnUrl == null) {
			throw new IllegalArgumentException();
		}

		this.cdnUrl = cdnUrl;
	}

	public String getImageUrl(FourchanPost post) throws ChanClientException {
		return this.resolveContentUrl(post, false);
	}

	public String getThumbUrl(FourchanPost post) throws ChanClientException {
		return this.resolveContentUrl(post, true);
	}

	private String resolveContentUrl(FourchanPost post, boolean isMiniature) throws ChanClientException {
		String extension = post.getFileExtension();
		long timeStamp = post.getTimeStamp();

		if (extension == null || timeStamp == 0) {
			throw new ChanClientException("Post doesn't have any media content");
		}

		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(cdnUrl).append("/").append(post.getBoard()).append("/").append(timeStamp);

		if (isMiniature) {
			sbUrl.append("s.jpg");
		} else {
			sbUrl.append(extension);
		}

		return sbUrl.toString();
	}

}