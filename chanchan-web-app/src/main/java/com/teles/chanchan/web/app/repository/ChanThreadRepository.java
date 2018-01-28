package com.teles.chanchan.web.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.web.app.document.mongo.ChanThread;

@Repository
public interface ChanThreadRepository extends MongoRepository<ChanThread, String> {

	Optional<ChanThread> findByNumberAndBoard(int number, String board);

	List<ChanThread> findByBoard(String board);
	
}
