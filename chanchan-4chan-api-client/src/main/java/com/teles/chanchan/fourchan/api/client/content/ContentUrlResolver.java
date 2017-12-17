package com.teles.chanchan.fourchan.api.client.content;

import org.springframework.stereotype.Component;

import com.teles.chanchan.config.settings.Client4ChanSettings;
import com.teles.chanchan.fourchan.api.client.dto.response.PostResponse;

@Component
public class ContentUrlResolver {

	private static final String SLASH = "/";

	private final String miniatureSuffix;
	private final String cdnUrl;

	public ContentUrlResolver(Client4ChanSettings settings) {
		this.miniatureSuffix = settings.getMiniatureSuffix();
		this.cdnUrl = settings.getCdnUrl();
	}

	public String buildMediaUrl(PostResponse post) {
		return new StringBuilder().append(this.resolveContentUrl(post)).append(post.getFileExtension()).toString();
	}

	public String buildThumbNailUrl(PostResponse post) {
		return new StringBuilder().append(this.resolveContentUrl(post)).append(this.miniatureSuffix).toString();
	}

	public boolean hasMedia(PostResponse post) {
		return post.getFileExtension() != null && post.getTimeStamp() != 0;
	}

	private String resolveContentUrl(PostResponse post) {
		return new StringBuilder().append(this.cdnUrl).append(SLASH).append(post.getBoard()).append(SLASH)
				.append(post.getTimeStamp()).toString();
	}

}