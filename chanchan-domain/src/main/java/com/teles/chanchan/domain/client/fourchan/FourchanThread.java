package com.teles.chanchan.domain.client.fourchan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "4chan-thread")
public class FourchanThread{
	
	@Id
	@JsonProperty("no")
	private Integer number;

	@JsonProperty("sub")
	private String name;

	@JsonProperty("com")
	private String description;

	@JsonProperty("semantic_url")
	private String semanticUrl;

	private String board;

	private List<FourchanPost> posts = new ArrayList<>();

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

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

	public List<FourchanPost> getPosts() {
		return posts;
	}

	public void setPosts(List<FourchanPost> posts) {
		this.posts = posts;
	}
	
	public void addPosts(List<FourchanPost> posts) {
		this.posts.addAll(posts);
	}

	@Override
	public String toString() {
		return "FourchanThread [number=" + number + ", name=" + name + ", description=" + description + ", semanticUrl="
				+ semanticUrl + ", board=" + board + ", posts=" + posts + "]";
	}

}