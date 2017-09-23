package com.teles.chanchan.domain.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post extends ChanchanEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "number")
	private Integer number;

	@Column(name = "timestamp")
	private Long timeStamp;

	@Column(name = "comentary")
	private String comentary;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "thread_id")
	private Thread thread;

	@OneToOne(mappedBy = "post", fetch = FetchType.LAZY)
	private PostContent postContent;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getComentary() {
		return comentary;
	}

	public void setComentary(String comentary) {
		this.comentary = comentary;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public PostContent getPostContent() {
		return postContent;
	}

	public void setPostContent(PostContent postContent) {
		this.postContent = postContent;
	}

	@Override
	public String toString() {
		return "Post [number=" + number + ", timeStamp=" + timeStamp + ", comentary=" + comentary + "]";
	}

}
