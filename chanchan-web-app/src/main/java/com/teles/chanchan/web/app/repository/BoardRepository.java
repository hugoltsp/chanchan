package com.teles.chanchan.web.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.web.app.document.mongo.ChanBoard;

@Repository
public interface BoardRepository extends MongoRepository<ChanBoard, String>{

	Optional<ChanBoard> findByBoard(String board);
}
