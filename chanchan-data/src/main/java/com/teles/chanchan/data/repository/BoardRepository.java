package com.teles.chanchan.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.domain.document.ChanBoard;

@Repository
public interface BoardRepository extends MongoRepository<ChanBoard, String>{

	ChanBoard findByBoard(String board);
}
