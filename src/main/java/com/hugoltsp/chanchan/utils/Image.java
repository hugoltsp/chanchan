package com.hugoltsp.chanchan.utils;

import java.util.Arrays;

public class Image {

	private String name;
	private byte[] file;

	public Image() {
	}

	public Image withName(String name) {
		this.name = name;
		return this;
	}

	public Image withFile(byte[] file) {
		this.file = file;
		return this;
	}

	public String getName() {
		return name;
	}

	public byte[] getFile() {
		return file;
	}

	@Override
	public String toString() {
		return "Image [name=" + name + ", file=" + Arrays.toString(file) + "]";
	}

}