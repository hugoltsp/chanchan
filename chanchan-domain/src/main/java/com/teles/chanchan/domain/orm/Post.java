package com.teles.chanchan.domain.orm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "post")
public class Post extends ChanchanEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "number")
	private Integer number;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timeStamp;

	@Column(name = "comentary")
	private String comentary;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "thread_id")
	private Thread thread;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_content_id")
	private PostContent postContent;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
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
