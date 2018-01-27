package com.teles.chanchan.fourchan.api.client.content;

import org.springframework.stereotype.Component;

import com.teles.chanchan.fourchan.api.client.dto.response.PostResponse;
import com.teles.chanchan.fourchan.api.client.settings.Client4ChanSettings;

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
		return new StringBuilder().append(buildContentUrl(post)).append(post.getFileExtension()).toString();
	}

	public String buildThumbNailUrl(PostResponse post) {
		return new StringBuilder().append(buildContentUrl(post)).append(this.miniatureSuffix).toString();
	}

	private String buildContentUrl(PostResponse post) {
		return new StringBuilder().append(this.cdnUrl).append(SLASH).append(post.getBoard()).append(SLASH)
				.append(post.getTimeStamp()).toString();
	}

}