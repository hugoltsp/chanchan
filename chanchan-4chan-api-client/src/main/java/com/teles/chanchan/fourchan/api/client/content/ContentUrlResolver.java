package com.teles.chanchan.fourchan.api.client.content;

import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.response.PostResponse;
import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.domain.settings.ChanchanSettings.ClientFourChan;
import com.teles.chanchan.fourchan.api.client.exception.ChanchanClientException;

@Component
public class ContentUrlResolver {

	private static final String SLASH = "/";

	private final String miniatureSuffix;
	private final String cdnUrl;

	public ContentUrlResolver(ChanchanSettings settings) {
		ClientFourChan clientFourChan = settings.getClientFourChan();
		this.miniatureSuffix = clientFourChan.getMiniatureSuffix();
		this.cdnUrl = clientFourChan.getCdnUrl();
	}

	public String buildMediaUrl(PostResponse post) throws ChanchanClientException {
		return new StringBuilder().append(this.resolveContentUrl(post)).append(post.getFileExtension()).toString();
	}

	public String buildThumbNailUrl(PostResponse post) throws ChanchanClientException {
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