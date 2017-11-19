package com.teles.chanchan.config.settings;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("chanchan.client-four-chan")
public class Client4ChanSettings {

	@NotBlank
	private String apiUrl;

	@NotBlank
	private String cdnUrl;
	
	private int requestDelay;

	@NotBlank
	private String miniatureSuffix;

	public int getRequestDelay() {
		return requestDelay;
	}

	public void setRequestDelay(int requestDelay) {
		this.requestDelay = requestDelay;
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

	public String toString() {
		return "Client4Chan [apiUrl=" + apiUrl + ", cdnUrl=" + cdnUrl + ", requestDelay=" + requestDelay
				+ ", miniatureSuffix=" + miniatureSuffix + "]";
	}
}
