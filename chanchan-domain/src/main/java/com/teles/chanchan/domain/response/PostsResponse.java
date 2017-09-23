package com.teles.chanchan.domain.response;

import java.util.List;

public class PostsResponse {

	private List<PostResponse> posts;

	public List<PostResponse> getPosts() {
		return posts;
	}

	public void setPosts(List<PostResponse> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "PostsResponse [posts=" + posts + "]";
	}

}