package com.hugoltsp.chanchan.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.hugoltsp.chanchan.exception.ImageWriteException;
import com.hugoltsp.chanchan.utils.Image;

@Service
public class ImageWriter {

	private static final Logger logger = LoggerFactory.getLogger(ImageWriter.class);

	private static final int BUFFER_SIZE = 16384;

	private final String outputPath;

	@Inject
	public ImageWriter(Environment environment) {
		this.outputPath = environment.getProperty("chanchan.output.path");
	}

	public void writeImage(Image image) throws ImageWriteException {
		OutputStream outputStream = null;

		try {
			String path = this.outputPath + image.getName();
			logger.info("Writing image to:: {}", path);

			File file = new File(path);
			file.getParentFile().mkdirs();
			file.createNewFile();

			outputStream = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
			outputStream.write(image.getFile());
			outputStream.flush();
		} catch (IOException e) {
			throw new ImageWriteException(e);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}

	}

}