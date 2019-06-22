package com.teles.chanchan.scraper.api.client.content;

import org.springframework.stereotype.Component;

import com.teles.chanchan.scraper.api.client.response.PostResponse;
import com.teles.chanchan.scraper.api.client.settings.Client4ChanSettings;

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