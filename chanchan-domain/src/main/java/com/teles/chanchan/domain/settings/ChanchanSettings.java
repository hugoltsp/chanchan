package com.teles.chanchan.domain.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "chanchan")
public class ChanchanSettings {

	private final ClientFourChan clientFourChan = new ClientFourChan();
	private final Io io = new Io();
	private final Async async = new Async();

	public ClientFourChan getClientFourChan() {
		return clientFourChan;
	}

	public Io getIo() {
		return io;
	}

	public Async getAsync() {
		return async;
	}

	public static final class ClientFourChan {

		private String apiUrl;
		private String cdnUrl;
		private int requestDelay;
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

	public static final class Async {

		private int threadPoolSize;
		private String threadNamePrefix;

		public String getThreadNamePrefix() {
			return threadNamePrefix;
		}

		public void setThreadNamePrefix(String threadNamePrefix) {
			this.threadNamePrefix = threadNamePrefix;
		}

		public int getThreadPoolSize() {
			return threadPoolSize;
		}

		public void setThreadPoolSize(int threadPoolSize) {
			this.threadPoolSize = threadPoolSize;
		}

		public String toString() {
			return "Async [threadPoolSize=" + threadPoolSize + ", threadNamePrefix=" + threadNamePrefix + "]";
		}

	}

	public static final class Io {

		private String outputPath;

		public String getOutputPath() {
			return outputPath;
		}

		public void setOutputPath(String outputPath) {
			this.outputPath = outputPath;
		}

		public String toString() {
			return "Io [outputPath=" + outputPath + "]";
		}

	}

}