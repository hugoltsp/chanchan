package com.teles.chanchan.fourchan.client.url;

import org.springframework.stereotype.Component;

import com.teles.chanchan.domain.FourchanPost;
import com.teles.chanchan.domain.exception.ChanchanClientException;
import com.teles.chanchan.domain.settings.ChanchanSettings;
import com.teles.chanchan.domain.settings.ChanchanSettings.ClientFourChan;

@Component
public class PostContentUrlResolver {

	private static final String SLASH = "/";
	
	private final ChanchanSettings settings;

	public PostContentUrlResolver(ChanchanSettings settings) {
		this.settings = settings;
	}

	public String getImageUrl(FourchanPost post) throws ChanchanClientException {
		return this.resolveContentUrl(post, false);
	}

	public String getThumbUrl(FourchanPost post) throws ChanchanClientException {
		return this.resolveContentUrl(post, true);
	}

	private String resolveContentUrl(FourchanPost post, boolean isMiniature) throws ChanchanClientException {
		String extension = post.getFileExtension();
		long timeStamp = post.getTimeStamp();
		ClientFourChan clientFourChan = this.settings.getClientFourChan();

		if (extension == null || timeStamp == 0) {
			throw new ChanchanClientException("Post doesn't have any media content");
		}

		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(clientFourChan.getCdnUrl()).append(SLASH).append(post.getBoard()).append(SLASH).append(timeStamp);

		if (isMiniature) {
			sbUrl.append(clientFourChan.getMiniatureSuffix());
		} else {
			sbUrl.append(extension);
		}

		return sbUrl.toString();
	}

}