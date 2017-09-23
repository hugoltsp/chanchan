package com.teles.chanchan.domain.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThreadResponse extends SimpleThreadResponse {

	@JsonProperty("sub")
	private String name;

	@JsonProperty("com")
	private String description;

	@JsonProperty("semantic_url")
	private String semanticUrl;

	private String board;

	private List<PostResponse> posts;

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSemanticUrl() {
		return semanticUrl;
	}

	public void setSemanticUrl(String semanticUrl) {
		this.semanticUrl = semanticUrl;
	}

	public List<PostResponse> getPosts() {
		return posts;
	}

	public void setPosts(List<PostResponse> posts) {
		this.posts = posts;
	}

	public void addPosts(List<PostResponse> posts) {
		this.posts.addAll(posts);
	}

	@Override
	public String toString() {
		return "ThreadResponse [name=" + name + ", description=" + description + ", semanticUrl=" + semanticUrl
				+ ", board=" + board + ", posts=" + posts + ", getNumber()=" + getNumber() + ", getLastModified()="
				+ getLastModified() + "]";
	}

}