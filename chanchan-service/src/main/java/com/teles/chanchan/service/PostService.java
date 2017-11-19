package com.teles.chanchan.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teles.chanchan.domain.document.Post;
import com.teles.chanchan.domain.document.PostContent;
import com.teles.chanchan.domain.exception.ChanchanApiClientException;
import com.teles.chanchan.dto.api.client.response.PostResponse;
import com.teles.chanchan.fourchan.api.client.FourchanChanResourceClient;

@Service
public class PostService {

	private static final Logger logger = LoggerFactory.getLogger(PostService.class);

	private final FourchanChanResourceClient chanClient;

	public PostService(FourchanChanResourceClient chanClient) {
		this.chanClient = chanClient;
	}

	public List<Post> findPosts(String board, int number) {
		logger.info("searching for posts on thread {} of {}", number, board);

		List<Post> posts = new ArrayList<>();

		try {

			posts.addAll(this.chanClient.getPostsFromBoardAndThreadNumber(board, number).stream().map(this::buildPost)
					.collect(Collectors.toList()));

		} catch (ChanchanApiClientException e) {
			logger.error("Couldn't find posts on thread {}", number);
		}

		return posts;
	}

	private Post buildPost(PostResponse response) {

		Post post = new Post();
		post.setComentary(response.getComentary());
		post.setNumber(response.getNumber());

		if (response.getTimeStamp() != null) {
			post.setTimeStamp(new Date(response.getTimeStamp()));
		}

		post.setPostContent(buildPostContent(response));

		return post;
	}

	private PostContent buildPostContent(PostResponse response) {
		PostContent postContent = new PostContent();
		postContent.setContentUrl(response.getContentUrl());
		postContent.setFileExtension(response.getFileExtension());
		postContent.setFileSize(response.getFileSize());
		postContent.setImageHeight(response.getImageHeight());
		postContent.setImageWidth(response.getImageWidth());
		postContent.setMd5(response.getMd5());
		postContent.setOriginalFileName(response.getOriginalFileName());
		postContent.setThumbUrl(response.getThumbUrl());
		return postContent;
	}
}
