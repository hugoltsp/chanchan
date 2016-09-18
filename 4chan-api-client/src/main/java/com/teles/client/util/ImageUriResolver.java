package com.teles.client.util;

import com.teles.client.domain.response.Post;
import com.teles.client.exception.ChanClientException;

public class ImageUriResolver {

	private final String cdnUrl;

	public ImageUriResolver(String cdnUrl) throws IllegalArgumentException {
		if (cdnUrl == null) {
			throw new IllegalArgumentException();
		}

		this.cdnUrl = cdnUrl;
	}

	public String getPostImageUrl(Post post) throws ChanClientException {
		String extension = post.getFileExtension();
		long timeStamp = post.getTimeStamp();

		if (extension == null || timeStamp == 0) {
			throw new ChanClientException("Post doesn't contain an image");
		}

		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(cdnUrl).append("/").append(post.getBoard()).append("/").append(timeStamp).append(extension);
		return sbUrl.toString();
	}
}