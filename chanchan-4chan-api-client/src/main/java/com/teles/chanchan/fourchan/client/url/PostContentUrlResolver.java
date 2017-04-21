package com.teles.chanchan.fourchan.client.url;

import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.client.fourchan.FourchanPost;
import com.teles.chanchan.domain.exception.ChanClientException;
import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.domain.settings.ChanchanSettings.ClientFourChan;

@Component
public class PostContentUrlResolver {

	private static final String SLASH = "/";
	
	private final ClientFourChan settings;

	public PostContentUrlResolver(ChanchanSettings settings) {
		this.settings = settings.getClientFourChan();
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
		sbUrl.append(this.settings.getCdnUrl()).append(SLASH).append(post.getBoard()).append(SLASH).append(timeStamp);

		if (isMiniature) {
			sbUrl.append(this.settings.getMiniatureSuffix());
		} else {
			sbUrl.append(extension);
		}

		return sbUrl.toString();
	}

}