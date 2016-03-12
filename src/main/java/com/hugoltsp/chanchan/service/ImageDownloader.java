package com.hugoltsp.chanchan.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hugoltsp.chanchan.exception.ImageDownloadException;
import com.hugoltsp.chanchan.utils.Image;

@Service
public class ImageDownloader {

	private static final Logger logger = LoggerFactory.getLogger(ImageDownloader.class);

	private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");

	public Image downloadImageFromUrl(String url) throws ImageDownloadException, MalformedURLException {
		return downloadImageFromUrl(new URL(url));
	}

	/**
	 * 
	 * @param url
	 *            image source
	 * @return image representation containing it's name and bytes array
	 * @throws ImageDownloadException
	 */
	public Image downloadImageFromUrl(URL url) throws ImageDownloadException {
		if (IMAGE_EXTENSIONS.matcher(url.getPath()).matches() 
				&& !url.getPath().contains("s.")) {
			logger.info("Downloading image at:: {}", url.toString());
			try {
				byte[] bytes = IOUtils.toByteArray(url);
				return new Image().withFile(bytes).withName(url.getPath());
			} catch (IOException e) {
				throw new ImageDownloadException(e);
			}
		} else {
			throw new ImageDownloadException("Invalid URL Image Source: " + url.getPath());
		}

	}

}