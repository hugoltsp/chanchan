package com.hugoltsp.chanchan.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

import com.hugoltsp.chanchan.exception.ImageDownloadException;

public class ImageDownloader {

	private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");

	private ImageDownloader() {

	}

	public static Image downloadImageFromUrl(String url) throws ImageDownloadException, MalformedURLException {
		return downloadImageFromUrl(new URL(url));
	}
	
	/**
	 * 
	 * @param url image source
	 * @return image representation containing it's name and bytes array
	 * @throws ImageDownloadException
	 */
	public static Image downloadImageFromUrl(URL url) throws ImageDownloadException {

		if (IMAGE_EXTENSIONS.matcher(url.getPath()).matches()) {
			try {
				byte[] bytes = IOUtils.toByteArray(url);
				return new Image().withFile(bytes).withName(url.getPath());
			} catch (IOException e) {
				throw new ImageDownloadException(e);
			}
		} else {
			throw new ImageDownloadException("Invalid URL Image Source");
		}

	}

}