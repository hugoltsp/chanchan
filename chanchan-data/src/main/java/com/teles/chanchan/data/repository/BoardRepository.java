package com.teles.chanchan.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.domain.document.Board;

@Repository
public interface BoardRepository extends MongoRepository<Board, String>{

	Board findByBoard(String board);
}
