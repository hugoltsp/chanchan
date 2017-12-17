package com.teles.chanchan.domain.document;

public class ChanPostContent {

	private Long fileSize;

	private String thumbUrl;

	private String contentUrl;

	private String md5;

	private String originalFileName;

	private String fileExtension;

	private Integer imageWidth;

	private Integer imageHeight;

	private byte[] file;

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "PostContent [fileSize=" + fileSize + ", thumbUrl=" + thumbUrl + ", contentUrl=" + contentUrl + ", md5="
				+ md5 + ", originalFileName=" + originalFileName + ", fileExtension=" + fileExtension + ", imageWidth="
				+ imageWidth + ", imageHeight=" + imageHeight + "]";
	}

}
