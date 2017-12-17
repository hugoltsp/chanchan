package com.teles.chanchan.config.settings;

import javax.annotation.PostConstruct;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("chanchan.client-four-chan")
public class Client4ChanSettings {

	private static final Logger logger = LoggerFactory.getLogger(Client4ChanSettings.class);

	@NotBlank
	private String apiUrl;

	@NotBlank
	private String cdnUrl;

	@NotBlank
	private String miniatureSuffix;

	@PostConstruct
	private void init() {
		logger.info(toString());
	}

	public String getMiniatureSuffix() {
		return miniatureSuffix;
	}

	public void setMiniatureSuffix(String miniatureSuffix) {
		this.miniatureSuffix = miniatureSuffix;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getCdnUrl() {
		return cdnUrl;
	}

	public void setCdnUrl(String cdnUrl) {
		this.cdnUrl = cdnUrl;
	}

	@Override
	public String toString() {
		return "Client4ChanSettings [apiUrl=" + apiUrl + ", cdnUrl=" + cdnUrl + ", miniatureSuffix=" + miniatureSuffix
				+ "]";
	}

}
