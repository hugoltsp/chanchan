package com.hugoltsp.chanchan.service;

import javax.inject.Inject;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.hugoltsp.chanchan.utils.Image;

@Service
public class ImageWriter {

	private final String outputPath;

	@Inject
	public ImageWriter(Environment environment) {
		outputPath = environment.getProperty("chanchan.output.path");
	}
	
	public void writeImage(Image image){
		
	}
	
}
