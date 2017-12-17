package com.teles.chanchan.domain.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Thread.COLLECTION_NAME)
@CompoundIndex(def = "{'number': 1, 'board': -1}", unique = true)
public class Thread {

	public static final String COLLECTION_NAME = "threads";

	@Id
	private String id;

	@Indexed
	private Integer number;

	@Indexed
	private String board;

	private String name;

	private String description;

	private String semanticUrl;

	private Date creationDate;

	private Date lastModified;

	private List<Post> posts = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "Thread [name=" + name + ", description=" + description + ", semanticUrl=" + semanticUrl + ", number="
				+ number + ", lastModified=" + lastModified + "]";
	}

}
