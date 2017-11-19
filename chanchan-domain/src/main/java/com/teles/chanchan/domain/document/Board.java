package com.teles.chanchan.domain.document;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Board.COLLECTION_NAME)
public class Board extends ChanchanDocument {

	public static final String COLLECTION_NAME = "boards";

	@Indexed
	private String board;

	private String description;

	private String title;

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Board [board=" + board + ", description=" + description + ", title=" + title + "]";
	}

}