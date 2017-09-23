package com.teles.chanchan.domain.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post_content")
public class PostContent extends ChanchanEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "size")
	private Integer fileSize;

	@Column(name = "thumb_url")
	private String thumbUrl;

	@Column(name = "content_url")
	private String contentUrl;

	@Column(name = "md5")
	private String md5;

	@Column(name = "original_file_name")
	private String originalFileName;

	@Column(name = "file_extension")
	private String fileExtension;

	@Column(name = "image_width")
	private Integer imageWidth;

	@Column(name = "image_height")
	private Integer imageHeight;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
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

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "PostContent [fileSize=" + fileSize + ", thumbUrl=" + thumbUrl + ", contentUrl=" + contentUrl + ", md5="
				+ md5 + ", originalFileName=" + originalFileName + ", fileExtension=" + fileExtension + ", imageWidth="
				+ imageWidth + ", imageHeight=" + imageHeight + "]";
	}

}
