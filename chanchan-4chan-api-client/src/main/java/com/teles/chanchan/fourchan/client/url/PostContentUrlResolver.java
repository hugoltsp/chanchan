package com.teles.chanchan.fourchan.client.url;

import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.FourchanPost;
import com.teles.chanchan.domain.exception.ChanchanClientException;
import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.domain.settings.ChanchanSettings.ClientFourChan;

@Component
public class PostContentUrlResolver {

	private static final String SLASH = "/";

	private final String miniatureSuffix;
	private final String cdnUrl;

	public PostContentUrlResolver(ChanchanSettings settings) {
		ClientFourChan clientFourChan = settings.getClientFourChan();
		this.miniatureSuffix = clientFourChan.getMiniatureSuffix();
		this.cdnUrl = clientFourChan.getCdnUrl();
	}

	public String buildMediaUrl(FourchanPost post) throws ChanchanClientException {
		StringBuilder url = new StringBuilder();
		url.append(this.resolveContentUrl(post));
		url.append(post.getFileExtension());
		return url.toString();
	}

	public String buildThumbNailUrl(FourchanPost post) throws ChanchanClientException {
		StringBuilder url = new StringBuilder();
		url.append(this.resolveContentUrl(post));
		url.append(this.miniatureSuffix);
		return url.toString();
	}

	private String resolveContentUrl(FourchanPost post) {
		StringBuilder sbUrl = new StringBuilder().append(this.cdnUrl).append(SLASH).append(post.getBoard())
				.append(SLASH).append(post.getTimeStamp());
		return sbUrl.toString();
	}

	public boolean hasMedia(FourchanPost post) {
		return post.getFileExtension() != null && post.getTimeStamp() != 0;
	}

}